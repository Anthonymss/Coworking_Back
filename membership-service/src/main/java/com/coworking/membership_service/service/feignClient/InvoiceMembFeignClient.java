package com.coworking.membership_service.service.feignClient;

import com.coworking.membership_service.presentation.DTO.InvoiceRequest;
import com.coworking.membership_service.presentation.DTO.InvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "invoice-service")
public interface InvoiceMembFeignClient {

    @GetMapping("/api/v1/invoices/createMembership")
    ResponseEntity<InvoiceResponse> createMembershipInvoice(@RequestBody InvoiceRequest invoiceRequest);
}
