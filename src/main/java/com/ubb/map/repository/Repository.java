package com.ubb.map.repository;

import com.ubb.map.domain.HasId;
import com.ubb.map.exception.DuplicateEntryException;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 *
 */
public class Repository<Id, T extends HasId<Id>> implements RepositoryInterface<Id, T> {
    private Map<Id, T> items;

    public Repository() {
        this.items = new Hashtable<>();
    }

    /**
     * Inserts a new entity into the com.ubb.map.repository
     *
     * @param obj The object to be inserted
     * @throws DuplicateEntryException If there is already an entity with the same id
     */
    @Override
    public void insert(T obj) throws DuplicateEntryException {
        if (this.items.containsKey(obj.getId())) {
            throw new DuplicateEntryException();
        }
        this.items.put(obj.getId(), obj);
    }

    /**
     * Removes the entity from the com.ubb.map.repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    @Override
    public T delete(Id id) {
        T deleted = this.findById(id);
        this.items.remove(id);

        return deleted;
    }

    /**
     * Updates the given entity by identifying the entity from
     * within the com.ubb.map.repository by id
     *
     * @param entity The entity to be updated
     */
    @Override
    public void update(T entity) {
        T obj = this.findById(entity.getId());
        this.items.put(obj.getId(), entity);
    }

    /**
     * Searches for an entity with a given id
     *
     * @param id The id of the searched entity
     * @return Entity The searched entity
     * @throws NoSuchElementException If the searched entity is not found
     */
    @Override
    public T findById(Id id) {
        T obj = this.items.get(id);
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException("Could not find the entity with id " + id);
    }

    @Override
    public void addCollection(Collection<T> collection) throws DuplicateEntryException {
        for (T obj : collection) {
            this.insert(obj);
        }
    }

    @Override
    public long size() {
        return this.items.size();
    }

    /**
     * @return {@link GenericArray} The object containing all the entities
     */
    @Override
    public Collection<T> getAll() {
        return this.items.values();
    }
}
