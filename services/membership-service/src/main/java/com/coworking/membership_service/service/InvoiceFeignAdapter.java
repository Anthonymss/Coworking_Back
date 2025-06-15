package com.coworking.membership_service.service;

import com.coworking.membership_service.persistence.entity.UserMembership;
import com.coworking.membership_service.presentation.DTO.InvoiceResponse;

public interface InvoiceFeignAdapter {

    InvoiceResponse createInvoice(UserMembership userMembership, String paymentMethod);
}
