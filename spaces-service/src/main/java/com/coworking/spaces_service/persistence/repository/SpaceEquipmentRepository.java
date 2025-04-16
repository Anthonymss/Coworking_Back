package com.coworking.spaces_service.persistence.repository;

import com.coworking.spaces_service.persistence.entity.SpaceEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceEquipmentRepository extends JpaRepository<SpaceEquipment,Long> {
}
