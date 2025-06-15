package com.coworking.reservation_service.exception;

public class ReservationSaveException extends RuntimeException {
    public ReservationSaveException(String message) {
        super(message);
    }
}
