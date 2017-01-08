package com.ubb.map.repository.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.HasId;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.services.filters.types.PropertyFilter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrmRepository<Id, T extends HasId<Id>> implements RepositoryInterface<Id, T> {
    public static final Integer PAGE_SIZE = 10;

    Dao<T, Id>       dao;

    public OrmRepository(ConnectionSource connection, Class<T> tClass) {
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
    public Collection<T> getAll(int page) {
        return getAll(page, PAGE_SIZE);
    }

    @Override
    public Collection<T> getAll(int page, int perPage) {
        QueryBuilder<T, Id> qb = dao.queryBuilder();
        qb.setWhere(null);
        return getPaginated(qb, page, perPage);
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

    @Override
    public Collection<T> getFiltered(List<PropertyFilter> filters, int page, int perPage) {
        QueryBuilder<T, Id> queryBuilder = this.dao.queryBuilder();
        try {

            Where where = queryBuilder.where();
            int len = filters.size();
            for (int i = 0; i < len; i++) {
                filters.get(i).apply(where);
                if (i != len - 1) {
                    where.and();
                }
            }
            if (len == 0) {
                queryBuilder.setWhere(null);
            }
            return getPaginated(queryBuilder, page, perPage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<T> getFiltered(List<PropertyFilter> filters, int page) {
        return getFiltered(filters, page, PAGE_SIZE);
    }

    public Collection<T> getPaginated(QueryBuilder<T, Id> queryBuilder, int page, int perPage) {
        long offset = (page - 1) * perPage;
        try {
            queryBuilder.offset(offset).limit((long) perPage);

            return dao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
