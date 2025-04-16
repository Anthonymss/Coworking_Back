package com.coworking.reservation_service.service.feignclient;

import com.coworking.reservation_service.presentation.dto.InvoiceRequest;
import com.coworking.reservation_service.presentation.dto.InvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "invoice-service")
public interface InvoiceFeignClient {
    @GetMapping("/api/v1/invoices/create")
    ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest invoiceRequest);

}