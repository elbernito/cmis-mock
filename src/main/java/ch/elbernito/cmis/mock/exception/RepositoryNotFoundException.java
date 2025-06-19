package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a repository is not found.
 */
public class RepositoryNotFoundException extends RuntimeException {
    public RepositoryNotFoundException(String message) {
        super(message);
    }
}
