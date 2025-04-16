package com.coworking.spaces_service.persistence.entity;

import com.coworking.spaces_service.util.enums.SpaceType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "spaces")
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerHour;

    private String urlImage;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceType spaceType;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SpaceEquipment> spaceEquipments;

}
