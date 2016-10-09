package controller;

import domain.Department;
import repository.DepartmentRepository;
import validator.DepartmentValidator;
import java.util.NoSuchElementException;

/**
 *
 */
public class DepartmentController {
    private DepartmentRepository departmentRepository;
    private DepartmentValidator  departmentValidator;

    /**
     *
     * @param departmentRepository The repository class for Department entities
     * @param departmentValidator  The validator for Department entity
     */
    public DepartmentController(DepartmentRepository departmentRepository, DepartmentValidator departmentValidator) {
        this.departmentRepository = departmentRepository;
        this.departmentValidator = departmentValidator;
    }

    /**
     * Creates, validates, and inserts the Department in the repository
     *
     * @param name          The name of the department
     * @param numberOfSeats The numberOfSeats of the department
     * @return Department   The newly created Department entity
     */
    public Department create(String name, Integer numberOfSeats) {
        Department department = new Department(
                this.departmentRepository.getNextId(),
                name,
                numberOfSeats
        );

        this.departmentValidator.validate(department);
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
    public Department update(Integer id, String newName, Integer newNumberOfSeats) {
        Department department = this.departmentRepository.findById(id);

        department.setName(newName);
        department.setNumberOfSeats(newNumberOfSeats);
        this.departmentValidator.validate(department);
        this.departmentRepository.update(department);

        return department;
    }

    /**
     *
     * @param id                      The id of the department to be deleted
     * @return {@link Department}     The deleted entity
     * @throws NoSuchElementException If the department with given id is not found
     */
    public Department delete(Integer id) {
        return this.departmentRepository.delete(id);
    }
}
