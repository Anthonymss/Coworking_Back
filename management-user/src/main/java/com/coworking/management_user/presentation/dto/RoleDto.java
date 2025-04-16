package com.coworking.management_user.presentation.dto;

import com.coworking.management_user.utils.enums.RoleName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    private RoleName name;
    private String description;
}