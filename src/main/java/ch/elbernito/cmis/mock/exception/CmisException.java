package ch.elbernito.cmis.mock.exception;

/**
 * Base exception for all CMIS errors.
 * Contains an error code according to the CMIS 1.2 specification.
 */
public class CmisException extends RuntimeException {

    private final String errorCode;

    public CmisException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CmisException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
