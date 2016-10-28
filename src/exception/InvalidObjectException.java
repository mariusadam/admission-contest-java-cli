package exception;

/**
 * Created by marius on 10/16/16.
 */
public class InvalidObjectException extends Exception {
    public InvalidObjectException() {
    }

    public InvalidObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidObjectException(Throwable cause) {
        super(cause);
    }

    protected InvalidObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidObjectException(String message) {
        super(message);
    }
}
