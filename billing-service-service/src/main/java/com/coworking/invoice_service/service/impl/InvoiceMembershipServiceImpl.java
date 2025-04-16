package com.coworking.invoice_service.service.impl;

import com.coworking.invoice_service.persistence.entity.InvoiceMembership;
import com.coworking.invoice_service.persistence.repository.InvoiceMembershipRepository;
import com.coworking.invoice_service.presentation.dto.InvoiceMembershipRequest;
import com.coworking.invoice_service.presentation.dto.InvoiceMembershipResponse;
import com.coworking.invoice_service.service.InvoiceMembershipService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class InvoiceMembershipServiceImpl implements InvoiceMembershipService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.18");

    private final InvoiceMembershipRepository invoiceMembershipRepository;

    public InvoiceMembershipServiceImpl(InvoiceMembershipRepository invoiceMembershipRepository) {
        this.invoiceMembershipRepository = invoiceMembershipRepository;
    }

    @Override
    public InvoiceMembershipResponse createMembershipInvoice(InvoiceMembershipRequest invoiceMembershipRequest) {

        BigDecimal subtotal = invoiceMembershipRequest.totalCost();
        BigDecimal taxAmount = calculateTax(subtotal);
        BigDecimal totalCost = subtotal.add(taxAmount);

        InvoiceMembership invoiceMembership  = InvoiceMembership.builder()
                .invoiceNumber(generateInvoiceNumber())
                .userId(invoiceMembershipRequest.userId())
                .membershipId(invoiceMembershipRequest.membershipId())
                .fecha(LocalDate.now())
                .taxAmount(taxAmount)
                .subtotal(subtotal)
                .totalCost(totalCost)
                .paymentMethod(invoiceMembershipRequest.paymentMethod())
                .build();
        invoiceMembershipRepository.save(invoiceMembership);
        return convertToDto(invoiceMembership);
    }

    private BigDecimal calculateTax(BigDecimal subtotal) {
        return subtotal.multiply(TAX_RATE);
    }

    private String generateInvoiceNumber() {
        return "CWKM" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private InvoiceMembershipResponse convertToDto(InvoiceMembership invoiceMembership){
        return InvoiceMembershipResponse.builder()
                .id(invoiceMembership.getId())
                .invoiceNumber(invoiceMembership.getInvoiceNumber())
                .userId(invoiceMembership.getUserId())
                .membershipId(invoiceMembership.getMembershipId())
                .fecha(invoiceMembership.getFecha())
                .subtotal(invoiceMembership.getSubtotal())
                .taxAmount(invoiceMembership.getTaxAmount())
                .totalCost(invoiceMembership.getTotalCost())
                .paymentMethod(invoiceMembership.getPaymentMethod())
                .build();
    }
}
