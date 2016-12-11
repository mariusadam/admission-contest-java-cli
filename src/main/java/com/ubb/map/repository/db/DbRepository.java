package com.ubb.map.repository.db;

import com.ubb.map.domain.HasId;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.helper.mapper.MapperInterface;
import com.ubb.map.repository.RepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by marius on 11/20/16.
 */
public class DbRepository<Id, T extends HasId<Id>> implements RepositoryInterface<Id, T> {
    private Connection connection;
    private MapperInterface<Id, T> mapper;
    private String tableName;

    public DbRepository(Connection connection, MapperInterface<Id, T> mapper, String tableName) {
        this.connection = connection;
        this.mapper = mapper;
        this.tableName = tableName;
    }

    @Override
    public void insert(T obj) throws DuplicateEntryException {
        Map<String, String> props = this.mapper.toMap(obj);
        Set<String> keys = props.keySet();
        ArrayList<String> values = keys.stream().map(props::get).collect(Collectors.toCollection(ArrayList::new));

        String cols = String.join(", ", props.keySet());
        String vals = (new String(new char[props.size() - 1]).replace("\0", "?, ")) + "?";
        String query = String.format("INSERT INTO `%s` (%s) VALUES (%s)", this.tableName, cols, vals);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            for(int i = 0; i < values.size(); i++) {
                stmt.setString(i + 1, values.get(i));
            }

            stmt.execute();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public T delete(Id id) {
        T obj = this.findById(id);

        String query = String.format("DELETE FROM `%s` WHERE id=?", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            stmt.execute();

            return obj;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(T entity) {
        Map<String, String> props = this.mapper.toMap(entity);
        props.remove("id");

        ArrayList<String> set = new ArrayList<>();
        ArrayList<String> binds = new ArrayList<>();
        for(Map.Entry<String, String> entry : props.entrySet()) {
            binds.add(entry.getValue());
            set.add(entry.getKey() + "=?");
        }

        String setStr = String.join(", ", set);
        String where = "id=?";
        String query = String.format("UPDATE `%s` SET %s WHERE %s", this.tableName, setStr ,where);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);

            int i = 0;
            while (i < binds.size()) {
                stmt.setString(i + 1, binds.get(i));
                i++;
            }
            stmt.setString(i + 1, entity.getId().toString());

            stmt.execute();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public T findById(Id id) {
        String query = String.format("SELECT t.* FROM `%s` t WHERE t.id=?", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            T obj = null;

            if (rs.next()) {
                obj =  this.mapper.createObject(rs);
            }
            rs.close();

            if (obj == null) {
                throw new RepositoryException("Could not find the object with id " + id);
            }

            return obj;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Collection<T> getAll() {
        String query = String.format("SELECT t.* FROM `%s` t", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            Collection<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(this.mapper.createObject(rs));
            }
            rs.close();

            return result;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void addCollection(Collection<T> collection) {
        collection.forEach(this::insert);
    }

    @Override
    public long size() {
        String query = String.format("SELECT COUNT(*) as size FROM `%s`", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            int size = 0;
            while (rs.next()) {
                size = rs.getInt("size");
            }
            rs.close();

            return size;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
