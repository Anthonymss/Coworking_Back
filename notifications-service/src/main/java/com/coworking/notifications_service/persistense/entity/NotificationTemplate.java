package com.coworking.notifications_service.persistense.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notification_templates")
@Data
public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String type;
}