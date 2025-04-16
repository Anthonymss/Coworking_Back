package com.coworking.reservation_service.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SpaceResponseDto {
    private BigDecimal priceHour;
    private String spaceDescription;
}
