package com.coworking.notifications_service.persistense.repository;

import com.coworking.notifications_service.persistense.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long>{
    Optional<NotificationTemplate> findByName(String templateName);
}
