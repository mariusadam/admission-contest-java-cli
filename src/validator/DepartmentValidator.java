package validator;

import domain.Department;
import domain.Entity;
import exception.DepartmentException;

/**
 *
 */
public class DepartmentValidator implements ValidatorInterface{
    
    @Override
    public void validate(Entity obj) {
        if (!(obj instanceof Department)) {
            throw new DepartmentException("Invalid Candidate object.");
        }
        
        //// TODO: 10/14/16 properly validate the object 
    }
}
