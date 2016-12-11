package com.ubb.map.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String message) {
        super(message);
    }
}