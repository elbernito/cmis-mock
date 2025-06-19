package ch.elbernito.cmis.mock.exception;

/**
 * Exception für Validierungs-/Constraint-Verletzungen (HTTP 400).
 */
public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
