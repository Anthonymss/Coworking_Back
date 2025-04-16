package com.coworking.notifications_service.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
public class ReservationInvoiceDetailsResponse {
    private String invoiceNumber;
    private String spaceDetails;
    private String userDetails;
    private String durationRange;
    private LocalDateTime reservationDate;
    private Long reservationId;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal totalCost;
    private String paymentMethod;
    private String email;
    private Long user_id;
}
