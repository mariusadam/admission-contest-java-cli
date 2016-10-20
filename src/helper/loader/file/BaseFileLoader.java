package helper.loader.file;

import exception.DuplicateEntryException;
import exception.InvalidObjectException;
import repository.RepositoryInterface;
import validator.ValidatorInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Marius Adam
 */
public abstract class BaseFileLoader<T> implements FileLoaderInterface<T> {
    private ValidatorInterface<T> validator;

    public BaseFileLoader(ValidatorInterface<T> validator) {
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(RepositoryInterface<T> repository, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null) {
                T obj = this.createFromFormat(line);
                this.validator.validate(obj);
                repository.insert(obj);
            }
        } catch (IOException | DuplicateEntryException | InvalidObjectException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param  parts  The properties from which to create the entity
     * @return Entity The newly created object
     */
    protected abstract T createFromFormat(String parts) throws InvalidObjectException;
}
