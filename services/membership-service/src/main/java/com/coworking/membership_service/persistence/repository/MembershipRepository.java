package com.coworking.membership_service.persistence.repository;

import com.coworking.membership_service.persistence.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
