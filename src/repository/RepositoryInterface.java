package repository;

import domain.HasId;
import exception.DuplicateEntryException;
import util.GenericArray;

import java.util.Collection;

/**
 *
 */
public interface RepositoryInterface<Id, T extends HasId<Id>> {
    /**
     * Inserts a new entity into the repository
     *
     * @param obj The object to be inserted
     * @throws DuplicateEntryException If there is already an entity with the same id
     */
    void insert(T obj) throws DuplicateEntryException;
    /**
     * Removes the entity from the repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    T delete(Id id);

    /**
     * Updates the given entity by identifying the entity from
     * within the repository by id
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
     *
     * @return {@link GenericArray} The object containing all the entities
     */
    Collection<T> getAll();

    /**
     * Returns the id of the last inserted entity
     *
     * @return Integer The last inserted id
     */
    Id getLastId();

    /**
     * Returns the id of the next inserted entity
     *
     * @return Integer The next inserted id
     */
    Id getNextId();

    void addCollection(Collection<T> collection);

    int size();
}
