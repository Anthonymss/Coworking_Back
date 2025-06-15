package com.coworking.notifications_service.repository;

import com.coworking.notifications_service.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification,Long> {
}
