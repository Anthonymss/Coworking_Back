package com.coworking.invoice_service.service;

import com.coworking.invoice_service.entity.Invoice;
import com.coworking.invoice_service.dto.InvoiceRequest;
import com.coworking.invoice_service.dto.InvoiceResponse;

public interface InvoiceService {
    Invoice getInvoiceByReservationId(Long reservationId);

    InvoiceResponse createInvoice(InvoiceRequest invoiceRequest);

    void actualizarEstado(Long reservaId, String nuevoEstado);
}
