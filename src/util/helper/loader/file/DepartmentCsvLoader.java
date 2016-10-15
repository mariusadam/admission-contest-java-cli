package util.helper.loader.file;

import domain.Department;
import exception.DepartmentException;
import org.apache.commons.lang.StringUtils;
import repository.RepositoryInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by marius on 10/13/16.
 */
public class DepartmentCsvLoader<T extends Department> implements FileLoaderInterface<T> {
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void load(RepositoryInterface repository, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null) {
                repository.insert(createFromCsvString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param csvString The string from which to create the entity
     * @return Entity the newly created object
     */
    public static Department createFromCsvString(String csvString) {
        String[] parts = StringUtils.split(csvString, ",");
        if(parts.length != 3) {
            throw new DepartmentException(String.format("Cannot create a Candidate object with csv string %s", new Object[]{csvString}));
        } else {
            return new Department(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
        }
    }
}
