package com.coworking.spaces_service.dto.management;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentDto {
    private Long id;
    private String name;
    private String description;
}
