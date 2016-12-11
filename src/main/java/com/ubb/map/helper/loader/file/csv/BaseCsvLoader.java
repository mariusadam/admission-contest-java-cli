package com.ubb.map.helper.loader.file.csv;

import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.helper.loader.file.BaseFileLoader;
import com.ubb.map.validator.ValidatorInterface;

import java.util.regex.Pattern;

/**
 * @author Marius Adam
 */
public abstract class BaseCsvLoader<T> extends BaseFileLoader<T> {
    private String separator;
    private final static String DEFAULT_SEPARATOR = " | ";

    public BaseCsvLoader(ValidatorInterface<T> validator) {
        this(validator, DEFAULT_SEPARATOR);
    }

    public BaseCsvLoader(ValidatorInterface<T> validator, String separator) {
        super(validator);
        this.separator = separator;
    }

    @Override
    protected T createFromFormat(String parts) throws InvalidObjectException {
        return this.createFromArray(parts.split(Pattern.quote(this.separator)));
    }

    protected abstract T createFromArray(String[] parts) throws InvalidObjectException;
}
