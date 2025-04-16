package com.coworking.reservation_service.presentation.controller;

import com.coworking.reservation_service.presentation.dto.ReservationInvoiceDetailsResponse;
import com.coworking.reservation_service.presentation.dto.ReservationRequestDto;
import com.coworking.reservation_service.presentation.dto.TimeSlotDto;
import com.coworking.reservation_service.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reservation")
public class ReservationController {
    private final ReservationService reservationService;

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
}
