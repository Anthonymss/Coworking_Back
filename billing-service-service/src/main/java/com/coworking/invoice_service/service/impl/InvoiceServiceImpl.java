package com.coworking.invoice_service.service.impl;

import com.coworking.invoice_service.persistence.entity.Invoice;
import com.coworking.invoice_service.persistence.repository.InvoiceRepository;
import com.coworking.invoice_service.presentation.dto.InvoiceRequest;
import com.coworking.invoice_service.presentation.dto.InvoiceResponse;
import com.coworking.invoice_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private static final BigDecimal TAX_RATE = new BigDecimal("0.18");

    @Override
    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {
        BigDecimal subtotal = invoiceRequest.getTotalCost();//reservation
        BigDecimal taxAmount = calculateTax(subtotal);
        BigDecimal totalCost = subtotal.add(taxAmount);
        Invoice invoice = Invoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .reservationId(invoiceRequest.getReservationId())
                .userId(invoiceRequest.getUserId())
                .issueDate(LocalDateTime.now())
                .taxAmount(taxAmount)
                .subtotal(subtotal)
                .totalCost(totalCost)
                .paymentMethod(invoiceRequest.getPaymentMethod())
                .status("Paid")
                .build();
        invoiceRepository.save(invoice);
        return convertToDto(invoice);
    }

    private String generateInvoiceNumber() {
        return "K00" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    private BigDecimal calculateTax(BigDecimal subtotal) {
        return subtotal.multiply(TAX_RATE);
    }
    public Invoice getInvoiceByReservationId(Long reservationId) {
        return invoiceRepository.findByReservationId(reservationId);
    }
    private InvoiceResponse convertToDto(Invoice invoice){
        return InvoiceResponse.builder()
               .id(invoice.getId())
               .invoiceNumber(invoice.getInvoiceNumber())
               .reservationId(invoice.getReservationId())
               .userId(invoice.getUserId())
               .issueDate(invoice.getIssueDate())
               .subtotal(invoice.getSubtotal())
               .taxAmount(invoice.getTaxAmount())
               .totalCost(invoice.getTotalCost())
               .paymentMethod(invoice.getPaymentMethod())
               .status(invoice.getStatus())
               .build();
    }

}
