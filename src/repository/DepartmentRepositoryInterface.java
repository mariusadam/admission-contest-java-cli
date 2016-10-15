package repository;

import domain.Department;
import domain.Entity;

/**
 * Created by marius on 10/15/16.
 */
public interface DepartmentRepositoryInterface extends RepositoryInterface {
    @Override
    Department delete(Integer id);

    @Override
    Department findById(Integer id);

    @Override
    Department[] getAll();
}
