package com.ubb.map.services.crud;

import com.ubb.map.domain.Department;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.DepartmentRepository;
import com.ubb.map.validator.DepartmentValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Service class handling crud operations on Department entity
 */
@Singleton
public class DepartmentCrudService extends BaseCrudService<Integer, Department> {
    private DepartmentRepository repository;

    /**
     * @param departmentRepository The optionRepository class for Department entities
     * @param departmentValidator  The com.ubb.map.validator for Department entity
     */
    @Inject
    public DepartmentCrudService(
            DepartmentRepository departmentRepository,
            DepartmentValidator departmentValidator
    ) {
        super(departmentValidator);
        repository = departmentRepository;
    }

    /**
     * Creates, validates, and inserts the Department in the optionRepository
     *
     * @param name          The name of the department
     * @param numberOfSeats The numberOfSeats of the department
     * @return Department   The newly created Department entity
     */
    public Department create(String code, String name, String numberOfSeats) throws InvalidObjectException, DuplicateEntryException, SQLException {
        Department department = new Department();
        department.setCode(code);
        department.setName(name);
        department.setNumberOfSeats(getIntOrNull(numberOfSeats));

        this.validator.validate(department);
        this.repository.insert(department);

        return department;
    }

    /**
     * @return {@link Department} The updated department
     */
    public Department update(Department d) throws InvalidObjectException, SQLException, RepositoryException {
        return update(d.getId().toString(), d.getCode(), d.getName(), d.getNumberOfSeats().toString());
    }

    /**
     * @param id               The of the department to update
     * @param newName          The new name of the department
     * @param newNumberOfSeats The new numberOfSeats of the department
     * @return {@link Department} The updated department
     */
    public Department update(String id, String newCode, String newName, String newNumberOfSeats) throws InvalidObjectException, SQLException, RepositoryException {
        Department department = this.repository.findById(Integer.valueOf(id));

        department.setName(newName);
        department.setCode(newCode);
        department.setNumberOfSeats(Integer.parseInt(newNumberOfSeats));
        this.validator.validate(department);
        this.repository.update(department);

        return department;
    }

    /**
     * @param id The id of the department to be deleted
     * @return {@link Department}     The deleted entity
     * @throws NoSuchElementException If the department with given id is not found
     */
    public Department delete(String id) throws SQLException, RepositoryException {
        return this.repository.delete(getIntOrNull(id));
    }

    public Collection<String> suggestCodes(String partialCode) throws SQLException {
        return repository.suggestCodes(partialCode);
    }

    @Override
    protected RepositoryInterface<Integer, Department> getRepository() {
        return repository;
    }
}
