package util.helper.loader.file;

import domain.Department;
import exception.InvalidDepartmentException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by marius on 10/13/16.
 */
public class DepartmentCsvLoader<T extends Department> extends BaseFileLoader<T>{
    /**
     *
     * @param csvString The string from which to create the entity
     * @return Entity the newly created object
     */
    @Override
    public Department createFromFormat(String csvString) throws InvalidDepartmentException {
        String[] parts = StringUtils.split(csvString, ",");
        if(parts.length != 3) {
            throw new InvalidDepartmentException(String.format("Cannot create a Candidate object with csv string %s", new Object[]{csvString}));
        } else {
            return new Department(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
        }
    }
}
