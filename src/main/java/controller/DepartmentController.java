package controller;

import domain.Department;
import exception.DuplicateEntryException;
import exception.InvalidObjectException;
import helper.generator.RandomGenerator;
import repository.RepositoryInterface;
import validator.ValidatorInterface;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 *
 */
public class DepartmentController {
    private RepositoryInterface<String, Department> departmentRepository;
    private ValidatorInterface<Department>          validator;

    /**
     *
     * @param departmentRepository The optionRepository class for Department entities
     * @param departmentValidator  The validator for Department entity
     */
    public DepartmentController(RepositoryInterface<String, Department> departmentRepository, ValidatorInterface<Department> departmentValidator) {
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
                RandomGenerator.getRandomString(),
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
        Department department = this.departmentRepository.findById(id);

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
        return this.departmentRepository.delete(id);
    }

    public Collection<Department> getAll() {
        return this.departmentRepository.getAll();
    }
}
