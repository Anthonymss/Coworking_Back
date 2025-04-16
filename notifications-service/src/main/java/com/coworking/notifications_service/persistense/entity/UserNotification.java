package com.coworking.notifications_service.persistense.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name = "user_notifications")
@Data
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long templateId;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    private String status;
}