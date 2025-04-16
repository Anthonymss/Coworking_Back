package com.coworking.reservation_service.service;

import com.coworking.reservation_service.exception.ReservationConflictException;
import com.coworking.reservation_service.exception.ReservationSaveException;
import com.coworking.reservation_service.persistence.entity.Reservation;
import com.coworking.reservation_service.persistence.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationValidator {
    private final ReservationRepository reservationRepository;

    public void validateConflict(Reservation reservation) {
        boolean conflict = reservationRepository.existsBySpaceIdAndTimeRange(
                reservation.getSpaceId(),
                reservation.getStartDate(),
                reservation.getEndDate()
        );
        if (conflict) {
            throw new ReservationConflictException("El espacio ya está reservado en el horario seleccionado.");
        }
    }
    public void validatePastDates(Reservation reservation) {
        LocalDateTime now = LocalDateTime.now();
        if (reservation.getStartDate().isBefore(now) || reservation.getEndDate().isBefore(now)) {
            throw new ReservationSaveException("No se pueden realizar reservas en fechas pasadas.");
        }
    }
}