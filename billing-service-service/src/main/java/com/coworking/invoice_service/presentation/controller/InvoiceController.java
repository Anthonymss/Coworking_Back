package com.coworking.invoice_service.presentation.controller;
import com.coworking.invoice_service.persistence.entity.Invoice;
import com.coworking.invoice_service.presentation.dto.InvoiceMembershipRequest;
import com.coworking.invoice_service.presentation.dto.InvoiceMembershipResponse;
import com.coworking.invoice_service.presentation.dto.InvoiceRequest;
import com.coworking.invoice_service.presentation.dto.InvoiceResponse;
import com.coworking.invoice_service.service.InvoiceMembershipService;
import com.coworking.invoice_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceMembershipService invoiceMembershipService;

    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest)
    {
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceRequest));
    }

    @PostMapping("/createMembership")
    public ResponseEntity<?> createMembershipInvoice(@RequestBody InvoiceMembershipRequest invoiceMembershipRequest)
    {
        return ResponseEntity.ok(invoiceMembershipService.createMembershipInvoice(invoiceMembershipRequest));
    }
}
