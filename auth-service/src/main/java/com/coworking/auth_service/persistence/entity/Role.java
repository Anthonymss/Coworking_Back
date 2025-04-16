package com.coworking.auth_service.persistence.entity;

import com.coworking.auth_service.util.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;
    @Column(length = 255)
    private String description;
}