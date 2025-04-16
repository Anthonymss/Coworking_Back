package com.coworking.spaces_service.exception.advice;

import com.coworking.spaces_service.exception.InvalidJwtTokenException;
import com.coworking.spaces_service.exception.NotFoundSpace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.relation.RoleNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred FATALXD: " + ex.getMessage());
    }
    @ExceptionHandler(NotFoundSpace.class)
    public ResponseEntity<String> handleSpaceNotFoundException(NotFoundSpace ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<String> handleInvalidTokenOfGoogle(InvalidJwtTokenException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}
