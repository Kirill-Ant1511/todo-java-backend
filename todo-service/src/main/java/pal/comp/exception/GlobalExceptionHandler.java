package pal.comp.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ResponseExceptionDto> handleException(Exception e) {
        log.error(e.getMessage());
        var errorResponse = new ResponseExceptionDto(
                "Unexpected error occurred. Class error: " + e.getClass(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(exception = AuthenticationException.class)
    public ResponseEntity<ResponseExceptionDto> handleException(AuthenticationException e) {
        log.error(e.getMessage());
        var errorResponse = new ResponseExceptionDto(
                "User not authorized",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(exception = RestClientException.class)
    public ResponseEntity<ResponseExceptionDto> handleRestClientException(RestClientException e) {
        log.error(e.getMessage());
        ResponseExceptionDto errorResponse;
        HttpStatus status;
        if(e.getCause() instanceof AuthenticationException) {
            errorResponse = new ResponseExceptionDto(
                    "User not found or token expired",
                    e.getMessage(),
                    LocalDateTime.now()
            );
            status = HttpStatus.UNAUTHORIZED;
        } else if (e.getCause() instanceof AccessDeniedException) {
            errorResponse = new ResponseExceptionDto(
                    "User not authorized",
                    e.getMessage(),
                    LocalDateTime.now()
            );
            status = HttpStatus.FORBIDDEN;
        } else {
            errorResponse = new ResponseExceptionDto(
                    "RestClientException occurred. Class error: " + e.getClass(),
                    e.getMessage(),
                    LocalDateTime.now()
            );
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        return ResponseEntity.status(status).body(errorResponse);
    }


}
