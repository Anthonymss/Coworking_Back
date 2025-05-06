package com.coworking.reservation_service.exception;

public class InvalidJwtTokenException extends RuntimeException{
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
