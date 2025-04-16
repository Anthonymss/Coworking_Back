package com.coworking.reservation_service.service;

import com.coworking.reservation_service.persistence.entity.Reservation;
import com.coworking.reservation_service.presentation.dto.InvoiceResponse;

public interface InvoiceFeignAdapter {

    InvoiceResponse createInvoice(Reservation reservation, String paymentMethod);
}