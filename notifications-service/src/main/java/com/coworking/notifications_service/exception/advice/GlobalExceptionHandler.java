package com.coworking.notifications_service.exception.advice;

import com.coworking.notifications_service.exception.EmailSendFailed;
import com.coworking.notifications_service.exception.TempladeNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TempladeNotFound.class)
    public ResponseEntity<String> handleUserNotFoundException(TempladeNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(EmailSendFailed.class)
    public ResponseEntity<String> handleEmailSendFailed(EmailSendFailed ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}