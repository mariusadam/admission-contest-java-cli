package com.ubb.map.services;

import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.HasId;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.services.filters.types.PropertyFilter;
import com.ubb.map.validator.ValidatorInterface;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by marius on 15.01.2017.
 */
public class BaseCrudService<Id, T extends HasId<Id>> {
    protected RepositoryInterface<Id, T> repository;
    protected ValidatorInterface<T> validator;

    public BaseCrudService(RepositoryInterface<Id, T> repository, ValidatorInterface<T> validator) {
        this.repository = repository;
        this.validator = validator;
    }


    public Collection<T> getAll() {
        return this.repository.getAll();
    }

    public Collection<T> getAll(int page) {
        return repository.getAll(page);
    }

    public Collection<T> getFiltered(List<PropertyFilter> filters) {
        return this.repository.getFiltered(filters, 1);
    }

    public Collection<T> getFiltered(List<PropertyFilter> filters, int page, int perPage) {
        return this.repository.getFiltered(filters, page, perPage);
    }

    public Collection<T> getFiltered(List<PropertyFilter> filters, int page) {
        return this.repository.getFiltered(filters, page);
    }

    /**
     *
     * @param id                      The id of the object to be deleted
     * @return {@link Candidate}      The deleted object
     * @throws NoSuchElementException If the object with given id is not found
     */
    public T delete(Id id) {
        return this.repository.delete(id);
    }

    public int getNrOfPages(List<PropertyFilter> filters, int perPage) {
        int matches =  repository.countMatches(filters);
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
}
