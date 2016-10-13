package repository;

import domain.Entity;
import exception.DuplicateIdException;
import util.GenericArray;

import java.util.NoSuchElementException;

/**
 *
 */
public class Repository implements RepositoryInterface{
    private GenericArray<Entity> items;

    public Repository() {
        this.items = new GenericArray<Entity>();
    }

    /**
     * Inserts a new entity into the repository
     *
     * @param obj The object to be inserted
     * @throws DuplicateIdException If there is already an entity with the same id
     */
    @Override
    public void insert(Entity obj) {
        if (this.items.has(obj)) {
            throw new DuplicateIdException();
        }
        this.items.add(obj);
    }

    /**
     * Removes the entity from the repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    @Override
    public Entity delete(Integer id) {
        Entity deleted = this.findById(id);
        return this.items.removeAt(this.items.find(deleted));
    }

    /**
     * Updates the given entity by identifying the entity from
     * within the repository by id
     *
     * @param entity The entity to be updated
     */
    @Override
    public void update(Entity entity) {
        this.items.set(this.items.find(entity), entity);
    }

    /**
     * Searches for an entity with a given id
     *
     * @param id The id of the searched entity
     * @return Entity The searched entity
     * @throws NoSuchElementException If the searched entity is not found
     */
    @Override
    public Entity findById(Integer id) {
        for (int i = 0; i < this.items.getSize(); i++) {
            if (this.items.getAt(i).getId() == id) {
                return this.items.getAt(i);
            }
        }
        throw new NoSuchElementException("Could not find the entity with id " + id);
    }

    /**
     * Returns the id of the last inserted entity
     *
     * @return Integer The last inserted id
     */
    @Override
    public Integer getLastId() {
        Integer lastId = Integer.MIN_VALUE;
        for (int i = 0; i < this.items.getSize(); i++) {
            lastId = lastId < this.items.getAt(i).getId() ? this.items.getAt(i).getId() : lastId;
        }
        lastId = lastId == Integer.MIN_VALUE ? null : lastId;
        return lastId;
    }

    /**
     * Returns the id of the next inserted entity
     *
     * @return Integer The next inserted id
     */
    @Override
    public Integer getNextId() {
        return this.getLastId() == null ? 0 : this.getLastId() + 1;
    }

    /**
     *
     * @return {@link GenericArray} The object containing all the entities
     */
    @Override
    public GenericArray<Entity> getItems() {
        return this.items;
    }
}
