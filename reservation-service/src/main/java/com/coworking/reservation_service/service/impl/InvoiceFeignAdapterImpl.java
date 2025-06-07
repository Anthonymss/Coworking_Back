package com.coworking.reservation_service.service.impl;

import com.coworking.reservation_service.entity.Reservation;
import com.coworking.reservation_service.dto.InvoiceRequest;
import com.coworking.reservation_service.dto.InvoiceResponse;
import com.coworking.reservation_service.service.InvoiceFeignAdapter;
import com.coworking.reservation_service.service.feignclient.EsbFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceFeignAdapterImpl implements InvoiceFeignAdapter {
    private final EsbFeignClient invoiceFeignClient;

    @Override
    public InvoiceResponse createInvoice(Reservation reservation, String paymentMethod) {
        InvoiceRequest request = new InvoiceRequest(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getTotalCost(),
                paymentMethod
        );
        return invoiceFeignClient.createInvoice(request,"invoice-service").getBody();
    }
}