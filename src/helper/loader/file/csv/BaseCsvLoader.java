package helper.loader.file.csv;

import exception.InvalidObjectException;
import helper.loader.file.BaseFileLoader;
import validator.ValidatorInterface;

import java.util.regex.Pattern;

/**
 * @author Marius Adam
 */
public abstract class BaseCsvLoader<T> extends BaseFileLoader<T> {
    private String separator;
    public final static String DEFAULT_SEPARATOR = " | ";

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
