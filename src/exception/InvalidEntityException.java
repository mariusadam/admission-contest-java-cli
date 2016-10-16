package exception;

/**
 * Created by marius on 10/16/16.
 */
public class InvalidEntityException extends Exception {
    public InvalidEntityException() {
    }

    public InvalidEntityException(String message) {
        super(message);
    }
}
