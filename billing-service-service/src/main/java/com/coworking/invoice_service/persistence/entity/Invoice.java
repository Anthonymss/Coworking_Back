package com.coworking.invoice_service.persistence.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "issue_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime issueDate;
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
    @Column(name = "tax_amount", nullable = false)
    private BigDecimal taxAmount;
    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;
    @Column(name = "status", nullable = false)
    private String status;
}
