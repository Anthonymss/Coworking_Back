package com.coworking.reservation_service.service.impl;

import com.coworking.reservation_service.persistence.entity.Reservation;
import com.coworking.reservation_service.presentation.dto.InvoiceRequest;
import com.coworking.reservation_service.presentation.dto.InvoiceResponse;
import com.coworking.reservation_service.service.InvoiceFeignAdapter;
import com.coworking.reservation_service.service.feignclient.InvoiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceFeignAdapterImpl implements InvoiceFeignAdapter {
    private final InvoiceFeignClient invoiceFeignClient;

    @Override
    public InvoiceResponse createInvoice(Reservation reservation, String paymentMethod) {
        InvoiceRequest request = new InvoiceRequest(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getTotalCost(),
                paymentMethod
        );
        return invoiceFeignClient.createInvoice(request).getBody();
    }
}