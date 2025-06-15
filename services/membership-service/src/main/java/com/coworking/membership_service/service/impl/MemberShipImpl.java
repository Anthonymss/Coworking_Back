package com.coworking.membership_service.service.impl;

import com.coworking.membership_service.persistence.entity.Membership;
import com.coworking.membership_service.persistence.repository.MembershipRepository;
import com.coworking.membership_service.presentation.DTO.MembershipDTO;
import com.coworking.membership_service.service.MembershipService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberShipImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    public MemberShipImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }


    @Override
    public List<MembershipDTO> getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAll();
        return memberships.stream().map(this::convertToDto).toList();
    }

    @Override
    public MembershipDTO getById(Long id) {
        Membership membership = membershipRepository.findById(id).orElseThrow();
        return convertToDto(membership);
    }

    private MembershipDTO convertToDto(Membership membership) {
        MembershipDTO membershipDTO = new MembershipDTO(
                membership.getId(),
                membership.getName(),
                membership.getDescription(),
                membership.getPrice(),
                membership.getDuration(),
                membership.getUrlImage1(),
                membership.getUrlImage2(),
                membership.getUrlImage3(),
                membership.getType()
        );
        return membershipDTO;
    }

}
