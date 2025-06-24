package ch.elbernito.cmis.mock.exception;

/**
 * Global Exception for a 400 - Bad Request
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
