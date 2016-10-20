package validator;

import domain.Department;
import domain.Entity;
import exception.InvalidDepartmentException;
import exception.InvalidObjectException;

/**
 *
 */
public class DepartmentValidator implements ValidatorInterface<Department> {
    @Override
    public void validate(Department obj) throws InvalidDepartmentException {
        if (!(obj instanceof Department)) {
            throw new InvalidDepartmentException("Invalid Candidate object.");
        }
        
        //// TODO: 10/14/16 properly validate the object 
    }
}
