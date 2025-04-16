package com.coworking.spaces_service.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "sites")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Space> space;
}