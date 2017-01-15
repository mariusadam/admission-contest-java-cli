package com.ubb.map.validator;

import com.ubb.map.exception.InvalidObjectException;

import javax.inject.Singleton;

/**
 * Created by marius on 10/14/16.
 */
public interface ValidatorInterface<T> {
    void validate(T obj) throws InvalidObjectException;
}
