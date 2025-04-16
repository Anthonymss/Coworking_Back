package com.coworking.membership_service.service.impl;

import com.coworking.membership_service.persistence.entity.Membership;
import com.coworking.membership_service.persistence.entity.UserMembership;
import com.coworking.membership_service.persistence.repository.MembershipRepository;
import com.coworking.membership_service.persistence.repository.UserMembershipRepository;
import com.coworking.membership_service.presentation.DTO.InvoiceResponse;
import com.coworking.membership_service.presentation.DTO.MembershipInvoiceDetailsResponse;
import com.coworking.membership_service.presentation.DTO.UserMembershipDTO;
import com.coworking.membership_service.service.InvoiceFeignAdapter;
import com.coworking.membership_service.service.UserMembershipService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserMembershipImpl implements UserMembershipService {

    private final UserMembershipRepository userMembershipRepository;
    private final MembershipRepository membershipRepository;
    private final InvoiceFeignAdapter invoiceFeignAdapter;

    public UserMembershipImpl(UserMembershipRepository userMembershipRepository, MembershipRepository membershipRepository, InvoiceFeignAdapter invoiceFeignAdapter) {
        this.userMembershipRepository = userMembershipRepository;
        this.membershipRepository = membershipRepository;
        this.invoiceFeignAdapter = invoiceFeignAdapter;
    }

//    @Override
//    public void saveUserMembership(UserMembershipDTO UserMembershipDTO) {
//        UserMembership userMembership = new UserMembership();
//        userMembership.setUserId(UserMembershipDTO.userId());
//        Membership membership = membershipRepository.findById(UserMembershipDTO.membershipId())
//                .orElseThrow(() -> new RuntimeException("Membership not found"));
//        userMembership.setMembership(membership);
//        userMembership.setStartDate(UserMembershipDTO.startDate());
//        userMembership.setEndDate(UserMembershipDTO.endDate());
//        userMembership.setStatus(UserMembershipDTO.status());
//        userMembershipRepository.save(userMembership);
//    }

    @Override
    public MembershipInvoiceDetailsResponse saveMembership(UserMembershipDTO userMembershipDTO) {
        UserMembership userMembership = convertToEntity(userMembershipDTO);
        userMembershipRepository.save(userMembership);
        InvoiceResponse invoiceResponse = invoiceFeignAdapter.createInvoice(userMembership, userMembershipDTO.paymentMethod());
        MembershipInvoiceDetailsResponse membershipInvoiceDetailsResponse = MembershipInvoiceDetailsResponse.builder()
                .invoiceNumber(invoiceResponse.invoiceNumber())
                .tipo(userMembershipDTO.membershipId())
                .fecha(invoiceResponse.fecha())
                .subtotal(invoiceResponse.subtotal())
                .iva(invoiceResponse.taxAmount())
                .subtotal(invoiceResponse.subtotal())
                .total(invoiceResponse.totalCost())
                .metodoPago(invoiceResponse.paymentMethod())
                .fechaInicio(userMembershipDTO.startDate())
                .fechaFin(userMembershipDTO.endDate())
                .build();
        return membershipInvoiceDetailsResponse;
    }

    private UserMembership convertToEntity(UserMembershipDTO userMembershipDTO) {
        UserMembership userMembership = new UserMembership();
        Membership membership = membershipRepository.findById(userMembershipDTO.membershipId())
                .orElseThrow(() -> new RuntimeException("no encontrado"));
        userMembership.setUserId(userMembershipDTO.userId());
        userMembership.setMembership(membership);
        userMembership.setStartDate(userMembershipDTO.startDate());
        userMembership.setEndDate(userMembershipDTO.endDate());
        userMembership.setStatus(userMembershipDTO.status());
        return userMembership;
    }
}
