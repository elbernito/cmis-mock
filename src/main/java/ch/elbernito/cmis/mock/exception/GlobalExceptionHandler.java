package ch.elbernito.cmis.mock.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for CMIS exceptions.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CmisException.class)
    public ResponseEntity<Object> handleCmisException(CmisException ex) {
        log.error("CMIS Exception: {} - {}", ex.getErrorCode(), ex.getMessage());
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("errorCode", ex.getErrorCode());
        error.put("message", ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if ("notFound".equals(ex.getErrorCode())) {
            status = HttpStatus.NOT_FOUND;
        } else if ("unauthorized".equals(ex.getErrorCode())) {
            status = HttpStatus.UNAUTHORIZED;
        } else if ("conflict".equals(ex.getErrorCode())) {
            status = HttpStatus.CONFLICT;
        } else if ("constraint".equals(ex.getErrorCode())) {
            status = HttpStatus.BAD_REQUEST;
        } else if ("invalidArgument".equals(ex.getErrorCode())) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOther(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage(), ex);
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("errorCode", "internal");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
