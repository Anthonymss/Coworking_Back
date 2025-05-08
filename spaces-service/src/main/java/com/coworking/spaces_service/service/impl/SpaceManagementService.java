package com.coworking.spaces_service.service.impl;

import com.coworking.spaces_service.dto.management.CreateFullSpaceRequest;
import com.coworking.spaces_service.dto.management.EquipmentRequest;
import com.coworking.spaces_service.dto.management.SiteDto;
import com.coworking.spaces_service.entity.Equipment;
import com.coworking.spaces_service.entity.Site;
import com.coworking.spaces_service.entity.Space;
import com.coworking.spaces_service.entity.SpaceEquipment;
import com.coworking.spaces_service.repository.EquipmentRepository;
import com.coworking.spaces_service.repository.SiteRepository;
import com.coworking.spaces_service.repository.SpaceEquipmentRepository;
import com.coworking.spaces_service.repository.SpaceRepository;
import com.coworking.spaces_service.service.feingclient.StogeServiceFeingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SpaceManagementService {
    private final StogeServiceFeingClient stogeService;
    private final SiteRepository siteRepository;
    private final SpaceRepository spaceRepository;
    private final EquipmentRepository equipmentRepository;
    private final SpaceEquipmentRepository spaceEquipmentRepository;
    public String createFullSpace(CreateFullSpaceRequest req, MultipartFile imageFile) {
        String imageUrl = stogeService.upload(imageFile);
        Site site = siteRepository.findByNameAndAddressAndCityAndDistrict(
                req.getSiteName(), req.getSiteAddress(), req.getSiteCity(), req.getSiteDistrict()
        ).orElseGet(() -> siteRepository.save(Site.builder()
                .name(req.getSiteName())
                .address(req.getSiteAddress())
                .city(req.getSiteCity())
                .district(req.getSiteDistrict())
                .build()));

        Space space = Space.builder()
                .name(req.getName())
                .description(req.getDescription())
                .capacity(req.getCapacity())
                .pricePerHour(req.getPricePerHour())
                .urlImage(imageUrl)
                .spaceType(req.getSpaceType())
                .site(site)
                .build();
        spaceRepository.save(space);

        if (req.getEquipments() != null) {
            for (EquipmentRequest eq : req.getEquipments()) {
                Equipment equipment = equipmentRepository.findById(eq.getEquipmentId())
                        .orElseThrow(() -> new RuntimeException("Equipment not found: " + eq.getEquipmentId()));

                spaceEquipmentRepository.save(SpaceEquipment.builder()
                        .space(space)
                        .equipment(equipment)
                        .quantity(eq.getQuantity())
                        .build());
            }
        }

        return "Full space created with site and equipment";
    }

    public List<SiteDto> getAllSite() {
        return  this.siteRepository.findAll().stream().map(
                x->SiteDto.builder()
                        .id(x.getId())
                        .name(x.getName())
                        .address(x.getAddress())
                        .city(x.getCity())
                        .district(x.getDistrict())
                        .build()
        ).collect(Collectors.toList());
    }
}
