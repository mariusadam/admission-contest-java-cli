package com.ubb.map.services.crud;

import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.HasId;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.validator.ValidatorInterface;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by marius on 15.01.2017.
 */
public abstract class BaseCrudService<Id, T extends HasId<Id>> {
    protected ValidatorInterface<T> validator;

    public BaseCrudService(ValidatorInterface<T> validator) {
        this.validator = validator;
    }

    public Collection<T> getAll() throws SQLException {
        return getRepository().getAll();
    }

    public List<T> getAll(List<PropertyFilter> filters) throws SQLException {
        return getRepository().getAll(filters);
    }

    public Collection<T> getFiltered(List<PropertyFilter> filters, int page, int perPage) throws SQLException, RepositoryException {
        return getRepository().getFiltered(filters, page, perPage);
    }

    /**
     * @param id The id of the object to be deleted
     * @return {@link Candidate}      The deleted object
     * @throws NoSuchElementException If the object with given id is not found
     */
    public T delete(Id id) throws SQLException, RepositoryException {
        return getRepository().delete(id);
    }

    public int getNrOfPages(List<PropertyFilter> filters, int perPage) throws SQLException {
        int matches = getRepository().countMatches(filters);
        if (matches == 0) {
            return 0;
        } else {
            int result = matches / perPage;
            if (matches % perPage != 0) {
                result++;
            }

            return result;
        }
    }

    protected Integer getIntOrNull(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public void hydrate(List<T> items, HydrationType hydrationType) throws SQLException, RepositoryException {
        if (hydrationType == HydrationType.FULL) {
            hydrateFull(items);
        }
    }

    protected void hydrateFull(Collection<T> items) throws SQLException, RepositoryException {

    }

    protected abstract RepositoryInterface<Id, T> getRepository();
}
