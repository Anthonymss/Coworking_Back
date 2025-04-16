package com.coworking.invoice_service.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoice_membership")
public class InvoiceMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long membershipId;
    private String invoiceNumber;
    @CreationTimestamp
    private LocalDate fecha;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal totalCost;
    private String paymentMethod;
}
