package com.coworking.management_user.repository;


import com.coworking.management_user.entity.Role;
import com.coworking.management_user.utils.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(RoleName name);
}
