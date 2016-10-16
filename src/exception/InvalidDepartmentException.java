package exception;

/**
 *
 */
public class InvalidDepartmentException extends InvalidEntityException {
    public InvalidDepartmentException() {
    }

    public InvalidDepartmentException(String message) {
        super(message);
    }
}
