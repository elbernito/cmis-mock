package ch.elbernito.cmis.mock.exception;

/**
 * CMIS Unauthorized Exception.
 */
public class CmisUnauthorizedException extends CmisException {
    public CmisUnauthorizedException(String message) {
        super(message, "unauthorized");
    }
}
