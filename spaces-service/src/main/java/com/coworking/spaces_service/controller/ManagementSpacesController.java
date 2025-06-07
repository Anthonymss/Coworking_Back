package com.coworking.spaces_service.controller;

import com.coworking.spaces_service.dto.EquipmentForSpacesDto;
import com.coworking.spaces_service.dto.management.CreateFullSpaceRequest;
import com.coworking.spaces_service.dto.management.EquipmentDto;
import com.coworking.spaces_service.dto.management.SiteDto;
import com.coworking.spaces_service.service.impl.SpaceManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/management-spaces")
public class ManagementSpacesController {
    private final  SpaceManagementService service;//no probado-test
    @PostMapping(value = "/space/full", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createFullSpace(
            @RequestPart("data") String dataJson,
            @RequestPart("image") MultipartFile image) {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateFullSpaceRequest request = null;
        try {
            request = objectMapper.readValue(dataJson, CreateFullSpaceRequest.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al deserializar el objeto");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre es obligatorio");
        }

        return ResponseEntity.ok(this.service.createFullSpace(request,image));
    }
    @GetMapping("/sites")
    public ResponseEntity<List<SiteDto>> getAllSite(){
        return ResponseEntity.ok(this.service.getAllSite());
    }
    @GetMapping("/equipments")
    public ResponseEntity<List<EquipmentDto>> getAllEquipments(){
        return ResponseEntity.ok(this.service.getAllEquipments());
    }

}
