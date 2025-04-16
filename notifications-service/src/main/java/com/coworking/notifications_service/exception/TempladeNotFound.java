package com.coworking.notifications_service.exception;

public class TempladeNotFound extends RuntimeException{
    public TempladeNotFound(String message) {
        super("Template not found: "+message);
    }
}
