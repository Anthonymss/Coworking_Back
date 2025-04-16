package com.coworking.membership_service.service;

import com.coworking.membership_service.presentation.DTO.MembershipInvoiceDetailsResponse;
import com.coworking.membership_service.presentation.DTO.UserMembershipDTO;

public interface UserMembershipService {

    //void saveUserMembership(UserMembershipDTO UserMembershipDTO);

    MembershipInvoiceDetailsResponse saveMembership(UserMembershipDTO userMembershipDTO);
}
