package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a Version is not found.
 */
public class VersionNotFoundException extends RuntimeException {
    public VersionNotFoundException(String message) {
        super(message);
    }
}
