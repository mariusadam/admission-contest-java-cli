package exception;

/**
 * Created by marius on 10/16/16.
 */
public class InvalidObjectException extends Exception {
    public InvalidObjectException() {
    }

    public InvalidObjectException(String message) {
        super(message);
    }
}
