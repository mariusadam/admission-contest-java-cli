package exception;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String message) {
        super(message);
    }
}