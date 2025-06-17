package ch.elbernito.cmis.mock.exception;

/**
 * CMIS Storage Exception.
 */
public class CmisStorageException extends CmisException {
    public CmisStorageException(String message) {
        super(message, "storage");
    }
}
