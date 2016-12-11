package com.ubb.map.helper.loader.file;

import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.exception.LoaderException;
import com.ubb.map.validator.ValidatorInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
    public Collection<T> load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            Collection<T> result = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                T obj = this.createFromFormat(line);
                this.validator.validate(obj);
                result.add(obj);
            }

            return result;
        } catch (IOException e) {
            throw new LoaderException(e);
        }
    }

    /**
     *
     * @param  parts  The properties from which to create the entity
     * @return Entity The newly created object
     */
    protected abstract T createFromFormat(String parts) throws InvalidObjectException;
}
