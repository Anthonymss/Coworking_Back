package com.coworking.management_user.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_authentications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false,unique = true)
    private User user;
    @Column(name = "auth_provider", nullable = false)
    private String authProvider;
    @Column(name = "provider_id", nullable = false)
    private String providerId;
}
