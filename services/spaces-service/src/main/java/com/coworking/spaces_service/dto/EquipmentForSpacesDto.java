package com.coworking.spaces_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EquipmentForSpacesDto {
    private Long id;
    private String name;
    private String description;
    private int quantity;
}