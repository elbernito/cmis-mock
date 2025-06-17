package ch.elbernito.cmis.mock.exception;

/**
 * CMIS Conflict Exception.
 */
public class CmisConflictException extends CmisException {
    public CmisConflictException(String message) {
        super(message, "conflict");
    }
}
