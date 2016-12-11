package com.ubb.map.repository.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.HasId;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.RepositoryInterface;

import java.sql.SQLException;
import java.util.Collection;

public class OrmRepository<Id, T extends HasId<Id>> implements RepositoryInterface<Id, T> {
    protected Dao<T, Id>       dao;
    protected Class<T>         tClass;

    public OrmRepository(ConnectionSource connection, Class<T> tClass) {
        this.tClass = tClass;

        try {
            this.dao    = DaoManager.createDao(connection, tClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(T obj) throws DuplicateEntryException {
        try {
            this.dao.create(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T delete(Id id) {
        T obj = this.findById(id);
        try {
            this.dao.delete(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    @Override
    public void update(T entity) {
        try {
            this.dao.update(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T findById(Id id) {
        T obj = null;
        try {
            obj = this.dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (obj == null) {
            throw new RepositoryException("Could not find the object with id " + id);
        }

        return obj;
    }

    @Override
    public Collection<T> getAll() {
        try {
            return this.dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCollection(Collection<T> collection) {
        for (T obj : collection) {
            try {
                this.dao.create(obj);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public long size() {
        try {
            return this.dao.countOf();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
