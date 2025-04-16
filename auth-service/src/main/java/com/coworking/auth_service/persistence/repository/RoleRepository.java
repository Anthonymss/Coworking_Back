package com.coworking.auth_service.persistence.repository;

import com.coworking.auth_service.persistence.entity.Role;
import com.coworking.auth_service.util.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(RoleName name);
}
