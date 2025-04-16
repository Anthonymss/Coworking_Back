package com.coworking.spaces_service.exception;

public class NotFoundSpace extends RuntimeException{
    public NotFoundSpace(String message) {
        super(message);
    }
}
