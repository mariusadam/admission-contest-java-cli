package com.ubb.map.repository.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.HasId;
import com.ubb.map.domain.Timestampable;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.services.filters.PropertyFilter;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class OrmRepository<Id, T extends HasId<Id>> implements RepositoryInterface<Id, T> {
    public static final Integer PAGE_SIZE = 10;
    Dao<T, Id> dao;

    public OrmRepository(ConnectionSource connection, Class<T> tClass) {
        try {
            this.dao = DaoManager.createDao(connection, tClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(T obj) throws DuplicateEntryException, SQLException {
        T objFromDb = dao.queryForId(obj.getId());
        if (objFromDb != null || obj.getId() != null) {
            throw new DuplicateEntryException("Object with id " + obj.getId() + " already exists.");
        }
        this.dao.create(obj);
    }

    @Override
    public T delete(Id id) throws SQLException, RepositoryException {
        T obj = this.findById(id);
        this.dao.delete(obj);
        return obj;
    }

    @Override
    public Collection<T> getAll(int page) throws SQLException {
        return getAll(page, PAGE_SIZE);
    }

    @Override
    public Collection<T> getAll(int page, int perPage) throws SQLException {
        QueryBuilder<T, Id> qb = dao.queryBuilder();
        qb.setWhere(null);
        return getPaginated(qb, page, perPage);
    }

    @Override
    public void update(T entity) throws SQLException {
        if (entity instanceof Timestampable) {
            ((Timestampable) entity).setUpdatedAt(new Date());
        }
        this.dao.update(entity);
    }

    @Override
    public T findById(Id id) throws SQLException, RepositoryException {
        T obj = this.dao.queryForId(id);
        if (obj == null) {
            throw new RepositoryException("Could not find the object with id " + id);
        }
        return obj;
    }

    @Override
    public Collection<T> getAll() throws SQLException {
        return this.dao.queryForAll();
    }

    @Override
    public void addCollection(Collection<T> collection) throws SQLException {
        for (T obj : collection) {
            this.dao.create(obj);
        }
    }

    @Override
    public long size() throws SQLException {
        return this.dao.countOf();
    }

    @Override
    public Collection<T> getFiltered(List<PropertyFilter> filters, int page, int perPage) throws SQLException {
        QueryBuilder<T, Id> queryBuilder = this.dao.queryBuilder();

        return getPaginated(applyFilters(queryBuilder, filters), page, perPage);
    }

    private QueryBuilder<T, Id> applyFilters(QueryBuilder<T, Id> queryBuilder, List<PropertyFilter> filters) throws SQLException {
        Where where = queryBuilder.where();
        Boolean appliedAtLeastOnce = false;
        Boolean appliedLastTime = false;
        for (PropertyFilter filter : filters) {
            if (appliedLastTime) {
                where.and();
            }
            appliedLastTime = filter.apply(where);
            appliedAtLeastOnce = appliedLastTime ? true : appliedAtLeastOnce;
        }

        if (!appliedAtLeastOnce) {
            queryBuilder.setWhere(null);
        } else if (!appliedLastTime) {
            where.isNotNull("id");
        }

        return queryBuilder;
    }

    @Override
    public Collection<T> getFiltered(List<PropertyFilter> filters, int page) throws SQLException {
        return getFiltered(filters, page, PAGE_SIZE);
    }

    public Collection<T> getPaginated(QueryBuilder<T, Id> queryBuilder, int page, int perPage) throws SQLException {
        long limit = Math.max(0, perPage);
        long offset = Math.max(0, (page - 1) * perPage);
        queryBuilder.offset(offset).limit(limit);

        return dao.query(queryBuilder.prepare());
    }

    public int countMatches(List<PropertyFilter> filters) throws SQLException {
        QueryBuilder<T, Id> qb = dao.queryBuilder();
        qb = applyFilters(qb, filters);
        qb.setCountOf(true);

        return (int) dao.countOf(qb.prepare());
    }
}
