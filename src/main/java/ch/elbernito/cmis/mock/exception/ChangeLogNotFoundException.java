package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a ChangeLog is not found.
 */
public class ChangeLogNotFoundException extends RuntimeException {
    public ChangeLogNotFoundException(String message) {
        super(message);
    }
}
