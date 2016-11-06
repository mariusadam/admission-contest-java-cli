package repository;

import domain.Entity;
import domain.HasId;
import exception.DuplicateEntryException;
import exception.RepositoryException;
import util.GenericArray;

import java.util.*;

/**
 *
 */
public class Repository<Id, T extends HasId<Id>> implements RepositoryInterface<Id, T> {
    private Map<Id, T> items;

    public Repository() {
        this.items = new Hashtable<>();
    }

    /**
     * Inserts a new entity into the repository
     *
     * @param obj The object to be inserted
     * @throws DuplicateEntryException If there is already an entity with the same id
     */
    @Override
    public void insert(T obj) throws DuplicateEntryException {
        if (this.items.containsKey(obj.getId())) {
            throw new DuplicateEntryException();
        }
        this.items.put(obj.getId(),obj);
    }

    /**
     * Removes the entity from the repository
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
     * within the repository by id
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
    @SuppressWarnings("unchecked")
    @Override
    public T findById(Id id) {
        T obj = this.items.get(id);
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException("Could not find the entity with id " + id);
    }

    /**
     * Returns the id of the last inserted entity
     *
     * @return Integer The last inserted id
     */
    @Override
    public Id getLastId() {
        Integer lastId = Integer.MIN_VALUE;
        for (HasId<Id> e : this.items.values()) {
            lastId = lastId < (Integer) e.getId() ? (Integer) e.getId() : lastId;
        }

        lastId = lastId == Integer.MIN_VALUE ? null : lastId;
        //noinspection unchecked
        return (Id) lastId;
    }

    /**
     * Returns the id of the next inserted entity
     *
     * @return Integer The next inserted id
     */
    @Override
    public Id getNextId() {
        //noinspection unchecked
        return this.getLastId() == null ? (Id) (new Integer(0)) : (Id) (new Integer(((Integer) this.getLastId()) + 1));
    }

    @Override
    public void addCollection(Collection<T> collection) {
        for (T obj : collection) {
            try {
                this.insert(obj);
            } catch (DuplicateEntryException e) {
                throw new RepositoryException(e);
            }
        }
    }

    @Override
    public int size() {
        return this.items.size();
    }

    /**
     *
     * @return {@link GenericArray} The object containing all the entities
     */
    @Override
    public Collection<T> getAll() {
        return this.items.values();
    }
}
