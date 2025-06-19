package ch.elbernito.cmis.mock.exception;

/**
 * Exception thrown when a TypeDefinition is not found.
 */
public class TypeDefinitionNotFoundException extends RuntimeException {
    public TypeDefinitionNotFoundException(String message) {
        super(message);
    }
}
