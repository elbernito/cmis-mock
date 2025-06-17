package ch.elbernito.cmis.mock.exception;

/**
 * CMIS NotFound Exception.
 */
public class CmisNotFoundException extends CmisException {
    public CmisNotFoundException(String message) {
        super(message, "notFound");
    }
}
