package com.coworking.auth_service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id);
    }
    public UserNotFoundException() {
        super("User not found  ERROR :(");
    }

    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}