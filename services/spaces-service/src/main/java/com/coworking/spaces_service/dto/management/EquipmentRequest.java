package com.coworking.spaces_service.dto.management;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public  class EquipmentRequest {
    @NotNull
    private Long equipmentId;
    @NotNull
    @Min(1)
    private Integer quantity;
}
