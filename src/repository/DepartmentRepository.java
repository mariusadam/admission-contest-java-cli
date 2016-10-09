package repository;

import domain.Department;
import java.util.NoSuchElementException;

/**
 *
 */
public class DepartmentRepository extends Repository {

    public DepartmentRepository() {
        super();
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
