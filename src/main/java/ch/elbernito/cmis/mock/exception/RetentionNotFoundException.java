package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a Retention entry is not found.
 */
public class RetentionNotFoundException extends RuntimeException {
    public RetentionNotFoundException(String message) {
        super(message);
    }
}
