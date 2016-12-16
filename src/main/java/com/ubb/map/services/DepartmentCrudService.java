package com.ubb.map.services;

import com.ubb.map.domain.Department;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.helper.generator.RandomGenerator;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.DepartmentRepository;
import com.ubb.map.services.filters.types.PropertyFilter;
import com.ubb.map.validator.DepartmentValidator;
import com.ubb.map.validator.ValidatorInterface;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Observable;

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
    public Department create(String name, Integer numberOfSeats) throws InvalidObjectException, DuplicateEntryException {
        Department department = new Department(
                RandomGenerator.getRandomInt(),
                name,
                numberOfSeats
        );

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
    public Department update(String id, String newName, Integer newNumberOfSeats) throws InvalidObjectException {
        Department department = this.departmentRepository.findById(Integer.valueOf(id));

        department.setName(newName);
        department.setNumberOfSeats(newNumberOfSeats);
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

    public Collection<Department> getFiltered(Collection<PropertyFilter> filters) {
        return  this.departmentRepository.getFiltered(filters);
    }
}
