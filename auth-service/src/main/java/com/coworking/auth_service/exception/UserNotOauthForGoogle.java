package com.coworking.auth_service.exception;

public class UserNotOauthForGoogle extends RuntimeException{
    public UserNotOauthForGoogle(String message) {
        super(message);
    }
}
