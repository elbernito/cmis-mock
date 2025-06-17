package ch.elbernito.cmis.mock.exception;

/**
 * CMIS ContentAlreadyExists Exception.
 */
public class CmisContentAlreadyExistsException extends CmisException {
    public CmisContentAlreadyExistsException(String message) {
        super(message, "contentAlreadyExists");
    }
}
