package com.ubb.map.exception;

/**
 * Created by marius on 11/7/16.
 */
public class SaverException extends RuntimeException {
    public SaverException() {
        super();
    }

    public SaverException(String message) {
        super(message);
    }

    public SaverException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaverException(Throwable cause) {
        super(cause);
    }

    protected SaverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
