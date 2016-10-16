package exception;

/**
 *
 */
public class InvalidCandidateException extends InvalidEntityException{
    public InvalidCandidateException() {
    }

    public InvalidCandidateException(String message) {
        super(message);
    }
}
