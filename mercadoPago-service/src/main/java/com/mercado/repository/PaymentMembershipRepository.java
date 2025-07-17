package com.mercado.repository;

import com.mercado.domain.PaymentMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMembershipRepository extends JpaRepository<PaymentMembership, Long> {
}
