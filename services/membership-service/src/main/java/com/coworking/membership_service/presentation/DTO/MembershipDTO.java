package com.coworking.membership_service.presentation.DTO;

import com.coworking.membership_service.persistence.entity.Membership;
import com.coworking.membership_service.util.MembershipType;

import java.util.List;

public record MembershipDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer duration,
        String urlImage1,
        String urlImage2,
        String urlImage3,
        MembershipType type
) {
}
