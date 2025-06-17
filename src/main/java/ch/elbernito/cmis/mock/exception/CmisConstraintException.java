package ch.elbernito.cmis.mock.exception;

/**
 * CMIS Constraint Exception.
 */
public class CmisConstraintException extends CmisException {
    public CmisConstraintException(String message) {
        super(message, "constraint");
    }
}
