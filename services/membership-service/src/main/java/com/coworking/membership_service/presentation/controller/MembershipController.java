package com.coworking.membership_service.presentation.controller;

import com.coworking.membership_service.presentation.DTO.MembershipDTO;
import com.coworking.membership_service.presentation.DTO.MembershipInvoiceDetailsResponse;
import com.coworking.membership_service.presentation.DTO.UserMembershipDTO;
import com.coworking.membership_service.service.MembershipService;
import com.coworking.membership_service.service.UserMembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/membership")
public class MembershipController {

    private final MembershipService membershipService;
    private final UserMembershipService userMembershipService;

    public MembershipController(MembershipService membershipService, UserMembershipService userMembershipService) {
        this.membershipService = membershipService;
        this.userMembershipService = userMembershipService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipDTO>> getMemberships() {
        List<MembershipDTO> memberships = membershipService.getAllMemberships();
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipDTO> getMembershipById(@PathVariable Long id) {
        MembershipDTO membership = membershipService.getById(id);
        return ResponseEntity.ok(membership);
    }

    @PostMapping
    public ResponseEntity<MembershipInvoiceDetailsResponse> saveUserMembership(@RequestBody UserMembershipDTO userMembershipDTO) {
        return ResponseEntity.ok(userMembershipService.saveMembership(userMembershipDTO));
    }
}
