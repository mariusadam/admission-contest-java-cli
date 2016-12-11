package com.ubb.map.exception;

/**
 * Created by marius on 11/6/16.
 */
public class LoaderException extends RuntimeException {
    public LoaderException() {
        super();
    }

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoaderException(Throwable cause) {
        super(cause);
    }

    protected LoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
