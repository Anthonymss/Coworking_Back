    package com.coworking.invoice_service.controller;
    import com.coworking.invoice_service.dto.InvoiceMembershipRequest;
    import com.coworking.invoice_service.dto.InvoiceRequest;
    import com.coworking.invoice_service.entity.Invoice;
    import com.coworking.invoice_service.service.InvoiceMembershipService;
    import com.coworking.invoice_service.service.InvoiceService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/invoices")
    @RequiredArgsConstructor
    public class    InvoiceController {

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

        @PutMapping("/actualizar-estado/{reservaId}")
        public ResponseEntity<?> actualizarEstado(@PathVariable Long reservaId, @RequestParam String nuevoEstado) {
            invoiceService.actualizarEstado(reservaId, nuevoEstado);
            return ResponseEntity.ok().build();
        }
    }
