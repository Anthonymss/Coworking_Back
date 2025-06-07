package com.coworking.spaces_service.repository;

import com.coworking.spaces_service.entity.SpaceEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceEquipmentRepository extends JpaRepository<SpaceEquipment,Long> {
}
