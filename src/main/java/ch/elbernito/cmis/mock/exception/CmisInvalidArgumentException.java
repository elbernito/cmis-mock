package ch.elbernito.cmis.mock.exception;

/**
 * CMIS InvalidArgument Exception.
 */
public class CmisInvalidArgumentException extends CmisException {
    public CmisInvalidArgumentException(String message) {
        super(message, "invalidArgument");
    }
}
