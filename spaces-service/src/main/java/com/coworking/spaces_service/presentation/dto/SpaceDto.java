package com.coworking.spaces_service.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SpaceDto {
    private Long id;
    private String name;
    private String description;
    private Integer capacity;
    private BigDecimal pricePerHour;
    private String urlImage;
    private String siteName;
    private String address;
    private String spaceType;
    private List<EquipmentDto> ListEquipment;
}