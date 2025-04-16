package com.coworking.invoice_service.service;

import com.coworking.invoice_service.presentation.dto.InvoiceMembershipRequest;
import com.coworking.invoice_service.presentation.dto.InvoiceMembershipResponse;

public interface InvoiceMembershipService {

    InvoiceMembershipResponse createMembershipInvoice(InvoiceMembershipRequest invoiceMembershipRequest);
}
