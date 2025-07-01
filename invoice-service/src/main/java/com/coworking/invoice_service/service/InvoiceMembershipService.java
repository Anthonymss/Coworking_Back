package com.coworking.invoice_service.service;

import com.coworking.invoice_service.dto.InvoiceMembershipRequest;
import com.coworking.invoice_service.dto.InvoiceMembershipResponse;

public interface InvoiceMembershipService {

    InvoiceMembershipResponse createMembershipInvoice(InvoiceMembershipRequest invoiceMembershipRequest);
}
