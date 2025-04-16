package com.coworking.reservation_service.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDto {
    private LocalTime startTime;
    private LocalTime endTime;
}
