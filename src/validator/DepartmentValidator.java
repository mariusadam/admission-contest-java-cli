package validator;

import domain.Department;
import domain.Entity;
import exception.InvalidCandidateException;
import exception.InvalidDepartmentException;
import exception.InvalidObjectException;

/**
 *
 */
public class DepartmentValidator implements ValidatorInterface<Department> {
    @Override
    public void validate(Department obj) throws InvalidDepartmentException {
        if (obj == null) {
            throw new InvalidDepartmentException("Invalid Department object.");
        }

        StringBuilder sb = new StringBuilder();

        if (obj.getId() < 0) {
            sb.append("     Invalid id: ").append(obj.getId()).append('\n');
        }

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
