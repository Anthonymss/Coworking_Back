package com.coworking.reservation_service.service;

import com.coworking.reservation_service.dto.ReservationDetailsDto;
import com.coworking.reservation_service.dto.ReservationInvoiceDetailsResponse;
import com.coworking.reservation_service.dto.ReservationRequestDto;
import com.coworking.reservation_service.dto.TimeSlotDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    ReservationInvoiceDetailsResponse saveReservation(ReservationRequestDto reservationDto);
    List<TimeSlotDto> getOccupiedTimeSlots(Long spaceId, LocalDate date);
    List<ReservationDetailsDto> getAllReservationById(Long userId, LocalDateTime startDate, LocalDateTime endDate, Boolean status);
}