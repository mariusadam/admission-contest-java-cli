package validator;

import domain.Department;
import domain.Entity;
import exception.InvalidDepartmentException;

/**
 *
 */
public class DepartmentValidator implements ValidatorInterface{
    
    @Override
    public void validate(Entity obj) throws InvalidDepartmentException {
        if (!(obj instanceof Department)) {
            throw new InvalidDepartmentException("Invalid Candidate object.");
        }
        
        //// TODO: 10/14/16 properly validate the object 
    }
}
