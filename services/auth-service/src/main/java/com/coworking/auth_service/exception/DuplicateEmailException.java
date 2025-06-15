package com.coworking.auth_service.exception;

public class DuplicateEmailException extends RuntimeException  {

    public DuplicateEmailException(String email) {
        super("Email " + email + " already exists");
    }

}
