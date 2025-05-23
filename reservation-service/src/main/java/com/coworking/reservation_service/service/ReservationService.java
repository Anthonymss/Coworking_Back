package com.coworking.reservation_service.service;

import com.coworking.reservation_service.dto.ReservationInvoiceDetailsResponse;
import com.coworking.reservation_service.dto.ReservationRequestDto;
import com.coworking.reservation_service.dto.TimeSlotDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    ReservationInvoiceDetailsResponse saveReservation(ReservationRequestDto reservationDto);
    ReservationRequestDto getReservationById(Long id);

    List<TimeSlotDto> getOccupiedTimeSlots(Long spaceId, LocalDate date);
}