package com.coworking.reservation_service.exception;

public class InvalidTokenOfGoogleException extends RuntimeException {
    public InvalidTokenOfGoogleException(String message) {
        super(String.format("Invalid token of google %s", message));
    }

}
