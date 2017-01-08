package com.ubb.map.services;

import com.ubb.map.domain.Department;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.DepartmentRepository;
import com.ubb.map.services.filters.types.PropertyFilter;
import com.ubb.map.validator.DepartmentValidator;
import com.ubb.map.validator.ValidatorInterface;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class handling crud operations on Department entity
 */
@Singleton
public class DepartmentCrudService {
    private RepositoryInterface<Integer, Department> departmentRepository;
    private ValidatorInterface<Department>          validator;

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
        this.departmentRepository = departmentRepository;
        this.validator = departmentValidator;
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
        this.departmentRepository.insert(department);

        return department;
    }

    /**
     *
     * @param id                  The of the department to update
     * @param newName             The new name of the department
     * @param newNumberOfSeats    The new numberOfSeats of the department
     * @return {@link Department} The updated department
     */
    public Department update(String id, String newCode, String newName, String newNumberOfSeats) throws InvalidObjectException {
        Department department = this.departmentRepository.findById(Integer.valueOf(id));

        department.setName(newName);
        department.setCode(newCode);
        department.setNumberOfSeats(Integer.parseInt(newNumberOfSeats));
        this.validator.validate(department);
        this.departmentRepository.update(department);

        return department;
    }

    /**
     *
     * @param id                      The id of the department to be deleted
     * @return {@link Department}     The deleted entity
     * @throws NoSuchElementException If the department with given id is not found
     */
    public Department delete(String id) {
        return this.departmentRepository.delete(Integer.valueOf(id));
    }

    public Collection<Department> getAll() {
        return this.departmentRepository.getAll();
    }

    public Collection<Department> getAll(int page) {
        return departmentRepository.getAll(page);
    }

    public Collection<Department> getFiltered(List<PropertyFilter> filters) {
        return this.departmentRepository.getFiltered(filters, 1);
    }

    public Collection<Department> getFiltered(List<PropertyFilter> filters, int page) {
        return this.departmentRepository.getFiltered(filters, page);
    }
}
