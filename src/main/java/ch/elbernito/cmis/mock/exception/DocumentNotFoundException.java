package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a Document is not found.
 */
public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
