package com.coworking.reservation_service.exception;

public class ReservationConflictException extends RuntimeException{
    public ReservationConflictException(String message) {

        super(message);
    }
}
