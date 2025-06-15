package com.coworking.membership_service.service.impl;

import com.coworking.membership_service.persistence.entity.UserMembership;
import com.coworking.membership_service.presentation.DTO.InvoiceRequest;
import com.coworking.membership_service.presentation.DTO.InvoiceResponse;
import com.coworking.membership_service.service.InvoiceFeignAdapter;
import com.coworking.membership_service.service.feignClient.InvoiceMembFeignClient;
import org.springframework.stereotype.Service;

@Service
public class InvoiceFeignAdapterImpl implements InvoiceFeignAdapter {

    private final InvoiceMembFeignClient invoiceMembFeignClient;

    public InvoiceFeignAdapterImpl(InvoiceMembFeignClient invoiceMembFeignClient) {
        this.invoiceMembFeignClient = invoiceMembFeignClient;
    }

    @Override
    public InvoiceResponse createInvoice(UserMembership userMembership, String paymentMethod) {
        InvoiceRequest request = new InvoiceRequest(
                userMembership.getId(),
                userMembership.getUserId(),
                userMembership.getMembership().getPrice(),
                paymentMethod
        );
        return invoiceMembFeignClient.createMembershipInvoice(request).getBody();
    }
}
