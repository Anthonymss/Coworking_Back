package com.coworking.management_user.persistence.entity;


import com.coworking.management_user.utils.enums.RoleName;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;
    @Column(length = 255)
    private String description;
}