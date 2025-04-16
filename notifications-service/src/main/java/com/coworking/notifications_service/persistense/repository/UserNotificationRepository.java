package com.coworking.notifications_service.persistense.repository;

import com.coworking.notifications_service.persistense.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification,Long> {
}
