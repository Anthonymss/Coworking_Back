package com.coworking.auth_service.exception;

public class UserNotAuthorization extends RuntimeException{
    public UserNotAuthorization(String message) {
        super(message);
    }
}
