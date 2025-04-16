package com.coworking.reservation_service.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {
    private Long reservationId;
    private Long userId;
    private BigDecimal totalCost;
    private String paymentMethod;

}
