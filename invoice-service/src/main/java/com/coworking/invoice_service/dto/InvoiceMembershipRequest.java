package com.coworking.invoice_service.dto;

import java.math.BigDecimal;

public record InvoiceMembershipRequest (
        Long membershipId,
        Long userId,
        BigDecimal totalCost,
        String paymentMethod
) {
}
