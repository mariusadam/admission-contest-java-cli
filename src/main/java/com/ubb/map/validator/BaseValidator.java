package com.ubb.map.validator;

import com.ubb.map.exception.InvalidObjectException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by marius on 1/15/2017.
 */
public class BaseValidator<T> implements ValidatorInterface<T> {
    protected Validator validator;

    public BaseValidator() {
        this(Validation.buildDefaultValidatorFactory().getValidator());
    }

    public BaseValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void validate(T obj) throws InvalidObjectException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        StringBuilder sb = new StringBuilder();
        constraintViolations.forEach(tConstraintViolation -> sb.append(tConstraintViolation.getMessage()).append(System.lineSeparator()));

        if (sb.length() > 0) {
            throw new InvalidObjectException(sb.toString());
        }
    }
}
