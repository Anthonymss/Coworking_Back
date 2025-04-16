package com.coworking.spaces_service.persistence.repository;

import com.coworking.spaces_service.persistence.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
}
