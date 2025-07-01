package com.coworking.reservation_service.service;

import com.coworking.reservation_service.entity.Reservation;
import com.coworking.reservation_service.dto.InvoiceResponse;

public interface InvoiceFeignAdapter {

    InvoiceResponse createInvoice(Reservation reservation, String paymentMethod);
}