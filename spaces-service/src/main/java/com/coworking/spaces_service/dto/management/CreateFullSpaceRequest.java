package com.coworking.spaces_service.dto.management;

import com.coworking.spaces_service.util.enums.SpaceType;
import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateFullSpaceRequest {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private BigDecimal pricePerHour;

    @NotNull
    private SpaceType spaceType;

    // Site
    @NotBlank
    private String siteName;

    @NotBlank
    private String siteAddress;

    @NotBlank
    private String siteCity;

    @NotBlank
    private String siteDistrict;
    private List<EquipmentRequest> equipments;


}
