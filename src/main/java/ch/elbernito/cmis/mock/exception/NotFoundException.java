package ch.elbernito.cmis.mock.exception;

/**
 * Exception für "nicht gefunden"-Fehler (HTTP 404).
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
