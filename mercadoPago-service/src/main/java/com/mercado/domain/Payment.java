package com.mercado.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount", nullable = false, unique = false)
    private double amount;
    @Column(name = "method", nullable = false, unique = false)
    private String method;
    @Column(name = "status", nullable = false, unique = false)
    private String status;
    @Column(name = "paymentDate", nullable = false)
    @CreationTimestamp
    private LocalDateTime paymentDate;
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

}