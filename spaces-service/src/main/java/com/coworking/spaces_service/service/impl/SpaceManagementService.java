package com.coworking.spaces_service.service.impl;

import com.coworking.spaces_service.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class SpaceManagementService {
    private final SpaceRepository repository;

    public String createSpace() {
    }
}
