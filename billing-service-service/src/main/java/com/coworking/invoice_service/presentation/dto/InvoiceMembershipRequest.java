package com.coworking.invoice_service.presentation.dto;

import java.math.BigDecimal;

public record InvoiceMembershipRequest (
        Long membershipId,
        Long userId,
        BigDecimal totalCost,
        String paymentMethod
) {
}
