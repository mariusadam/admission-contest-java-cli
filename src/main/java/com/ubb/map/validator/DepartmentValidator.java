package com.ubb.map.validator;

import com.ubb.map.domain.Department;
import com.ubb.map.exception.InvalidDepartmentException;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
public class DepartmentValidator implements ValidatorInterface<Department> {
    @Override
    public void validate(Department obj) throws InvalidDepartmentException {
        if (obj == null) {
            throw new InvalidDepartmentException("Invalid Department object.");
        }

        StringBuilder sb = new StringBuilder();

        if (obj.getName() == null || obj.getName().isEmpty()) {
            sb.append("     Invalid name: ").append(obj.getName()).append('\n');
        }

        if (obj.getNumberOfSeats() == null || obj.getNumberOfSeats() < 0) {
            sb.append("     Invalid number of seats: ").append(obj.getNumberOfSeats()).append('\n');
        }

        if (sb.length() > 0) {
            throw new InvalidDepartmentException("\nInvalid department: \n" + sb.toString());
        }
    }
}
