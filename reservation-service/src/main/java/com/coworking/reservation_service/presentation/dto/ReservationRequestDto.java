package com.coworking.reservation_service.presentation.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {
    private Long id;
    private Long spaceId;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean status;
    private BigDecimal totalCost;
    private String comments;
    private String email;
    private Long user_id;
    //other
    private String paymentMethod;

}