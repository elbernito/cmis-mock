package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a Object is not found.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
