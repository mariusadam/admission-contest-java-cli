package repository;

import domain.Entity;
import exception.DuplicateEntryException;
import util.GenericArray;

import java.util.Collection;

/**
 *
 */
public interface RepositoryInterface<T> {
    /**
     * Inserts a new entity into the repository
     *
     * @param obj The object to be inserted
     * @throws DuplicateEntryException If there is already an entity with the same id
     */
    public void insert(T obj) throws DuplicateEntryException;
    /**
     * Removes the entity from the repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    public T delete(Integer id);

    /**
     * Updates the given entity by identifying the entity from
     * within the repository by id
     *
     * @param entity The entity to be updated
     */
    public void update(T entity);

    /**
     * Searches for an entity with a given id
     *
     * @param id The id of the searched entity
     * @return Entity The searched entity
     * @throws java.util.NoSuchElementException If the searched entity is not found
     */
    public T findById(Integer id);

    /**
     *
     * @return {@link GenericArray} The object containing all the entities
     */
    public Collection<T> getAll();

    /**
     * Returns the id of the last inserted entity
     *
     * @return Integer The last inserted id
     */
    public Integer getLastId();

    /**
     * Returns the id of the next inserted entity
     *
     * @return Integer The next inserted id
     */
    public Integer getNextId();
}
