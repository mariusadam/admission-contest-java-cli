package com.ubb.map.repository;

import com.ubb.map.domain.HasId;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.services.filters.types.PropertyFilter;
import com.ubb.map.util.GenericArray;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public interface RepositoryInterface<Id, T extends HasId<Id>> {
    /**
     * Inserts a new entity into the com.ubb.map.repository
     *
     * @param obj The object to be inserted
     * @throws DuplicateEntryException If there is already an entity with the same id
     */
    void insert(T obj) throws DuplicateEntryException;

    /**
     * Removes the entity from the com.ubb.map.repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    T delete(Id id);

    /**
     * Updates the given entity by identifying the entity from
     * within the com.ubb.map.repository by id
     *
     * @param entity The entity to be updated
     */
    void update(T entity);

    /**
     * Searches for an entity with a given id
     *
     * @param id The id of the searched entity
     * @return Entity The searched entity
     * @throws java.util.NoSuchElementException If the searched entity is not found
     */
    T findById(Id id);

    /**
     * @return {@link GenericArray} The object containing all the entities
     */
    Collection<T> getAll();

    default Collection<T> getAll(int page) {
        return null;
    }

    default Collection<T> getAll(int page, int perPage) {
        return null;
    }

    /**
     * @param collection A collection of objects to insert into the com.ubb.map.repository
     */
    void addCollection(Collection<T> collection);

    /**
     * @return int
     */
    long size();

    default Collection<T> getFiltered(List<PropertyFilter> filters, int page, int perPage) {
        return null;
    }

    default Collection<T> getFiltered(List<PropertyFilter> filters, int page) {
        return null;
    }
}
