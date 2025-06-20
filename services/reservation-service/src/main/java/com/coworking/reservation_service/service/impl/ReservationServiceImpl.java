package com.coworking.reservation_service.service.impl;

import com.coworking.reservation_service.dto.*;
import com.coworking.reservation_service.entity.Reservation;
import com.coworking.reservation_service.repository.ReservationRepository;
import com.coworking.reservation_service.service.*;
import com.coworking.reservation_service.service.operation_cost.CostCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final SpacesFeignAdapter spacesFeignAdapter;
    private final InvoiceFeignAdapter invoiceFeignAdapter;
    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;
    private final CostCalculator costCalculator;
    private final NotificationService notificationService;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
    DateTimeFormatter reservationDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

    @Transactional
    @Override
    public ReservationInvoiceDetailsResponse saveReservation(ReservationRequestDto reservationDto) {
        Reservation reservation = convertToEntity(reservationDto);
        reservationValidator.validateConflict(reservation);
        reservationValidator.validatePastDates(reservation);
        SpaceResponseDto spaceResponseDto = spacesFeignAdapter.getSpaceInfo(reservationDto.getSpaceId());
        reservation.setTotalCost(costCalculator.calculate(spaceResponseDto.getPriceHour(), reservation));
        Reservation savedReservation = reservationRepository.save(reservation);
        InvoiceResponse invoiceResponse = invoiceFeignAdapter.createInvoice(savedReservation, reservationDto.getPaymentMethod());
        ReservationInvoiceDetailsResponse invoiceDetailsResponse =ReservationInvoiceDetailsResponse.builder()
                .invoiceNumber(invoiceResponse.getInvoiceNumber())
                .reservationId(savedReservation.getId())
                .spaceDetails(spaceResponseDto.getSpaceDescription())
                .reservationDate(savedReservation.getReservationDate().format(reservationDateFormatter))
                .subtotal(invoiceResponse.getSubtotal())
                .taxAmount(invoiceResponse.getTaxAmount())
                .totalCost(invoiceResponse.getTotalCost())
                .paymentMethod(invoiceResponse.getPaymentMethod())
                .email(reservationDto.getEmail())
                .user_id(reservationDto.getUserId())
                .durationRange(
                        savedReservation.getStartDate().format(dateTimeFormatter)
                                + " - " +
                                savedReservation.getEndDate().format(dateTimeFormatter))
                .build();
        System.out.println("Datos Invoice: "+invoiceDetailsResponse);
        notificationService.sendReservationEmailAsync("ReservationTemplate",invoiceDetailsResponse );

        return invoiceDetailsResponse;
    }

    @Override
    public ReservationRequestDto getReservationById(Long id) {
        return null;
    }

    @Override
    public List<TimeSlotDto> getOccupiedTimeSlots(Long spaceId, LocalDate date) {
        List<Reservation> reservations = reservationRepository.findBySpaceIdAndDate(spaceId, date);
        return reservations.stream()
                .map(reservation -> new TimeSlotDto(convertToLocalTime(reservation.getStartDate()), convertToLocalTime(reservation.getEndDate())))
                .collect(Collectors.toList());
    }
    private Reservation convertToEntity(ReservationRequestDto reservationDto){
        return Reservation.builder()
                .spaceId(reservationDto.getSpaceId())
                .userId(reservationDto.getUserId())
                .startDate(reservationDto.getStartDate())
                .endDate(reservationDto.getEndDate())
                .comments(reservationDto.getComments())
                .status(true)
                .build();
    }
    private static LocalTime convertToLocalTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }
}