package repository;

import domain.Department;
import domain.Entity;
import exception.DepartmentException;

import java.util.NoSuchElementException;

/**
 *
 */
public class DepartmentRepository extends Repository implements DepartmentRepositoryInterface{

    public DepartmentRepository() {
        super();
    }

    /**
     * Inserts a new entity into the repository
     *
     * @param obj The object to be inserted
     * @throws exception.DuplicateIdException If there is already an entity with the same id
     */
    @Override
    public void insert(Entity obj) {
        if (!(obj instanceof Department)) {
            throw new DepartmentException("Inserted objects must be of type Department.");
        }

        super.insert(obj);
    }

    /**
     * Removes the entity from the repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    @Override
    public Department delete(Integer id) {
        return (Department) super.delete(id);
    }

    /**
     * Searches for an entity with a given id
     *
     * @param id The id of the searched entity
     * @return Entity The searched entity
     * @throws NoSuchElementException If the searched entity is not found
     */
    @Override
    public Department findById(Integer id) {
        return (Department) super.findById(id);
    }
}
