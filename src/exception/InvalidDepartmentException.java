package exception;

/**
 *
 */
public class InvalidDepartmentException extends InvalidObjectException {
    public InvalidDepartmentException() {
    }

    public InvalidDepartmentException(String message) {
        super(message);
    }
}
