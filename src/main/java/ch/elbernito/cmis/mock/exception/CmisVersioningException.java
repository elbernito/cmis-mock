package ch.elbernito.cmis.mock.exception;

/**
 * CMIS Versioning Exception.
 */
public class CmisVersioningException extends CmisException {
    public CmisVersioningException(String message) {
        super(message, "versioning");
    }
}
