package com.coworking.auth_service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
