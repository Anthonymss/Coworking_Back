package com.coworking.spaces_service.dto.management;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class SiteDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String district;
}
