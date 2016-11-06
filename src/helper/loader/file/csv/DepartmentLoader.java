package helper.loader.file.csv;

import domain.Department;
import exception.InvalidDepartmentException;
import helper.loader.file.BaseFileLoader;
import validator.ValidatorInterface;

import java.util.Arrays;

/**
 * @author Marius Adam
 */
public class DepartmentLoader extends BaseCsvLoader<Department> {
    public DepartmentLoader(ValidatorInterface<Department> validator) {
        super(validator);
    }

    public DepartmentLoader(ValidatorInterface<Department> validator, String propertySeparator) {
        super(validator, propertySeparator);
    }

    /**
     *
     * @param  parts  The properties from which to create the entity
     * @return Entity The newly created object
     */
    @Override
    public Department createFromArray(String[] parts) throws InvalidDepartmentException {
        if(parts.length != 3) {
            throw new InvalidDepartmentException(String.format("Cannot create a Candidate object with csv string %s", Arrays.toString(parts)));
        } else {
            return new Department(parts[0], parts[1], Integer.parseInt(parts[2]));
        }
    }
}
