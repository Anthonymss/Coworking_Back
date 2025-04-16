package com.coworking.management_user.persistence.repository;

import com.coworking.management_user.persistence.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository  extends JpaRepository<UserAuthentication,Long> {
    UserAuthentication findByUser_Id(Long userId);
}
