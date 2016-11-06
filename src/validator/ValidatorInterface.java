package validator;

import exception.InvalidObjectException;

/**
 * Created by marius on 10/14/16.
 */
public interface ValidatorInterface<T> {
    void validate(T obj) throws InvalidObjectException;
}
