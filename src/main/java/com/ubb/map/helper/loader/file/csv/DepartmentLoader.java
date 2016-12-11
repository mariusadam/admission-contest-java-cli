package com.ubb.map.helper.loader.file.csv;

import com.ubb.map.domain.Department;
import com.ubb.map.exception.InvalidDepartmentException;
import com.ubb.map.validator.ValidatorInterface;

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
            return new Department(Integer.valueOf(parts[0]), parts[1], Integer.parseInt(parts[2]));
        }
    }
}
