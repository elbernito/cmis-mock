package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a Folder is not found.
 */
public class FolderNotFoundException extends RuntimeException {
    public FolderNotFoundException(String message) {
        super(message);
    }
}
