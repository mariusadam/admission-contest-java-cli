package com.ubb.map.services;

import com.ubb.map.domain.Department;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.repository.db.DepartmentRepository;
import com.ubb.map.validator.DepartmentValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.NoSuchElementException;

/**
 * Service class handling crud operations on Department entity
 */
@Singleton
public class DepartmentCrudService extends BaseCrudService<Integer, Department> {
    /**
     *
     * @param departmentRepository The optionRepository class for Department entities
     * @param departmentValidator  The com.ubb.map.validator for Department entity
     */
    @Inject
    public DepartmentCrudService(
            DepartmentRepository departmentRepository,
            DepartmentValidator departmentValidator
    ) {
        super(departmentRepository, departmentValidator);
    }

    /**
     * Creates, validates, and inserts the Department in the optionRepository
     *
     * @param name          The name of the department
     * @param numberOfSeats The numberOfSeats of the department
     * @return Department   The newly created Department entity
     */
    public Department create(String code, String name, String numberOfSeats) throws InvalidObjectException, DuplicateEntryException {
        Integer noOFSeats = Integer.parseInt(numberOfSeats);

        Department department = new Department();
        department.setCode(code);
        department.setName(name);
        department.setNumberOfSeats(noOFSeats);

        this.validator.validate(department);
        this.repository.insert(department);

        return department;
    }

    /**
     *
     * @return {@link Department} The updated department
     */
    public Department update(Department d) throws InvalidObjectException {
        return update(d.getId().toString(), d.getCode(), d.getName(), d.getNumberOfSeats().toString());
    }

    /**
     *
     * @param id                  The of the department to update
     * @param newName             The new name of the department
     * @param newNumberOfSeats    The new numberOfSeats of the department
     * @return {@link Department} The updated department
     */
    public Department update(String id, String newCode, String newName, String newNumberOfSeats) throws InvalidObjectException {
        Department department = this.repository.findById(Integer.valueOf(id));

        department.setName(newName);
        department.setCode(newCode);
        department.setNumberOfSeats(Integer.parseInt(newNumberOfSeats));
        this.validator.validate(department);
        this.repository.update(department);

        return department;
    }

    /**
     *
     * @param id                      The id of the department to be deleted
     * @return {@link Department}     The deleted entity
     * @throws NoSuchElementException If the department with given id is not found
     */
    public Department delete(String id) {
        return this.repository.delete(Integer.valueOf(id));
    }
}
