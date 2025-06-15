package com.coworking.invoice_service.repository;

import com.coworking.invoice_service.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    Invoice findByReservationId(Long reservationId);
}
