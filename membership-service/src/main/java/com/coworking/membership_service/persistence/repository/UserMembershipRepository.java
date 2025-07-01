package com.coworking.membership_service.persistence.repository;

import com.coworking.membership_service.persistence.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
}
