package com.coworking.spaces_service.repository;

import com.coworking.spaces_service.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
}
