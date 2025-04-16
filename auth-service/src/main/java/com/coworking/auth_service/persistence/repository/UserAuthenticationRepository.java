package com.coworking.auth_service.persistence.repository;

import com.coworking.auth_service.persistence.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository  extends JpaRepository<UserAuthentication,Long> {
}
