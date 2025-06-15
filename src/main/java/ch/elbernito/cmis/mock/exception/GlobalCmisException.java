package ch.elbernito.cmis.mock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Generic CMIS exception for unhandled errors.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GlobalCmisException extends RuntimeException {
    public GlobalCmisException(String message) {
        super(message);
    }
}
