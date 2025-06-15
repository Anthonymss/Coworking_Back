package com.coworking.membership_service.presentation.DTO;

public record InvoiceRequest(
        Long membershipId,
        Long userId,
        Double totalCost,
        String paymentMethod
) {

}
