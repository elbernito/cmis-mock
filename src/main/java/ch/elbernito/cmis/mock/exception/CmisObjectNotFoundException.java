package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a CMIS Object is not found.
 */
public class CmisObjectNotFoundException extends RuntimeException {
    public CmisObjectNotFoundException(String message) {
        super(message);
    }
}
