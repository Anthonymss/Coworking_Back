package com.coworking.auth_service.repository;

import com.coworking.auth_service.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository  extends JpaRepository<UserAuthentication,Long> {
}
