package com.coworking.notifications_service.exception;

public class EmailSendFailed extends RuntimeException{
    public EmailSendFailed(String email) {
        super("Failed to send email to: " + email);
    }

}
