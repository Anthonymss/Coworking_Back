package com.coworking.invoice_service.presentation.dto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@Data
public class InvoiceResponse {
    private Long id;
    private String invoiceNumber;
    private Long reservationId;
    private Long userId;
    private LocalDateTime issueDate;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal totalCost;
    private String paymentMethod;
    private String status;
}
