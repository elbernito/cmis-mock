package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a Policy is not found.
 */
public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException(String message) {
        super(message);
    }
}
