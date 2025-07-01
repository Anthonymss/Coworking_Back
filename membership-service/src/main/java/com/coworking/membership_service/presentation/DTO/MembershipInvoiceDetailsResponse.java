package com.coworking.membership_service.presentation.DTO;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record MembershipInvoiceDetailsResponse(
        String invoiceNumber,
        LocalDate fecha,
        Long tipo,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String metodoPago,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total
) {
}
