package com.mercado.repository;

import com.mercado.domain.PaymentMercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentMercado, Long> {
}
