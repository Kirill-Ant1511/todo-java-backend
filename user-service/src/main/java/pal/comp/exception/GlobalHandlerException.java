package pal.comp.exception;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {

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

    @ExceptionHandler(exception = EntityNotFoundException.class)
    public ResponseEntity<ResponseExceptionDto> entityNotFoundHandler(EntityNotFoundException e) {
        log.error(e.getMessage());
        var errorResponse = new ResponseExceptionDto(
                "Entity not found",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(exception = ExpiredJwtException.class)
    public ResponseEntity<ResponseExceptionDto> expiredJwtTokenExceptionHandler(ExpiredJwtException e) {
        log.error(e.getMessage());
        var errorResponse = new ResponseExceptionDto(
                "JWT Token expired",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
