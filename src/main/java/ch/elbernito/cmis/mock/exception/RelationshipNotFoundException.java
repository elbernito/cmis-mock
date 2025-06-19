package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a Relationship is not found.
 */
public class RelationshipNotFoundException extends RuntimeException {
    public RelationshipNotFoundException(String message) {
        super(message);
    }
}
