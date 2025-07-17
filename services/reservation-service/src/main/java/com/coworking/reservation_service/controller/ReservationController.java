package com.coworking.reservation_service.controller;

import com.coworking.reservation_service.configuration.jwt.JwtAuthenticationFilter;
import com.coworking.reservation_service.configuration.jwt.JwtTokenProvider;
import com.coworking.reservation_service.dto.ReservationDetailsDto;
import com.coworking.reservation_service.dto.ReservationInvoiceDetailsResponse;
import com.coworking.reservation_service.dto.ReservationRequestDto;
import com.coworking.reservation_service.dto.TimeSlotDto;
import com.coworking.reservation_service.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ReservationInvoiceDetailsResponse>saveResservation(@RequestBody ReservationRequestDto reservationDto){
        System.out.println("::::: => "+reservationDto);
        return ResponseEntity.ok(reservationService.saveReservation(reservationDto));
    }
    @GetMapping("/space/{spaceId}/occupied-times")
    public ResponseEntity<List<TimeSlotDto>> getOccupiedTimeSlots(
            @PathVariable Long spaceId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TimeSlotDto> occupiedTimes = reservationService.getOccupiedTimeSlots(spaceId, date);
        return ResponseEntity.ok(occupiedTimes);
    }
    @GetMapping
    public String saludo(){
        return "Hola desde ReservationController";
    }

    @GetMapping("/reservation")
    public ResponseEntity<List<ReservationDetailsDto>> getAllReservationById(
            HttpServletRequest request,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Boolean status
    ) {
        String token = jwtTokenProvider.resolveToken(request);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        List<ReservationDetailsDto> result = reservationService.getAllReservationById(userId, startDate, endDate, status);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
