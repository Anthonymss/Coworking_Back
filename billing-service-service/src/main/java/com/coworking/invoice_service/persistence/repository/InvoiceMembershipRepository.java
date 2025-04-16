package com.coworking.invoice_service.persistence.repository;

import com.coworking.invoice_service.persistence.entity.InvoiceMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceMembershipRepository extends JpaRepository<InvoiceMembership, Long> {
}
