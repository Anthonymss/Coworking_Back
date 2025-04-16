package com.coworking.invoice_service.presentation.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record InvoiceMembershipResponse (
        Long id,
        Long userId,
        Long membershipId,
        String invoiceNumber,
        LocalDate fecha,
        BigDecimal subtotal,
        BigDecimal taxAmount,
        BigDecimal totalCost,
        String paymentMethod
){
}
