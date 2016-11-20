package exception;

/**
 *
 */
public class InvalidCandidateException extends InvalidObjectException {
    public InvalidCandidateException() {
    }

    public InvalidCandidateException(String message) {
        super(message);
    }
}
