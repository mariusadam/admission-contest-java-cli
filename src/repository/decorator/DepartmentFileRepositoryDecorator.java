package repository.decorator;

import domain.Department;
import domain.Entity;
import exception.DepartmentException;
import repository.DepartmentRepositoryInterface;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public class DepartmentFileRepositoryDecorator extends RepositoryDecorator implements DepartmentRepositoryInterface{
    /**
     *
     * @param repository
     */
    public DepartmentFileRepositoryDecorator(RepositoryInterface repository) {
        super(repository);
    }

    @Override
    public void insert(Entity obj) {
        if (!(obj instanceof Department)) {
            throw new DepartmentException("Inserted objects must be of type Candidate.");
        }

        super.insert(obj);
    }

    @Override
    public Department delete(Integer id) {
        return (Department) super.delete(id);
    }

    @Override
    public Department findById(Integer id) {
        return (Department) super.findById(id);
    }

    @Override
    public Department[] getAll() {
        return (Department[]) super.getAll();
    }
}
