package com.coworking.invoice_service.presentation.dto;
import java.math.BigDecimal;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {
    private Long reservationId;
    private Long userId;
    private BigDecimal totalCost;
    private String paymentMethod;

}
