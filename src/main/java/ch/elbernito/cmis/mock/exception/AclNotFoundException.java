package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when an ACL is not found.
 */
public class AclNotFoundException extends RuntimeException {
    public AclNotFoundException(String message) {
        super(message);
    }
}
