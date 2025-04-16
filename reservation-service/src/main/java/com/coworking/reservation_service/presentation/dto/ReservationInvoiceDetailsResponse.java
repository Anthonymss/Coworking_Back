package com.coworking.reservation_service.presentation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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
