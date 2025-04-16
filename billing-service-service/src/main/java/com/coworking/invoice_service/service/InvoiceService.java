package com.coworking.invoice_service.service;

import com.coworking.invoice_service.persistence.entity.Invoice;
import com.coworking.invoice_service.presentation.dto.InvoiceMembershipRequest;
import com.coworking.invoice_service.presentation.dto.InvoiceMembershipResponse;
import com.coworking.invoice_service.presentation.dto.InvoiceRequest;
import com.coworking.invoice_service.presentation.dto.InvoiceResponse;

import java.math.BigDecimal;

public interface InvoiceService {
    Invoice getInvoiceByReservationId(Long reservationId);

    InvoiceResponse createInvoice(InvoiceRequest invoiceRequest);
}
