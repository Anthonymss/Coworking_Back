package com.coworking.reservation_service.service.impl;

import com.coworking.reservation_service.presentation.dto.SpaceResponseDto;
import com.coworking.reservation_service.service.SpacesFeignAdapter;
import com.coworking.reservation_service.service.feignclient.SpacesFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpacesFeignAdapterImpl implements SpacesFeignAdapter {
    private final SpacesFeignClient spacesFeignClient;

    @Override
    public SpaceResponseDto getSpaceInfo(Long spaceId) {
        return spacesFeignClient.getInfoSpace(spaceId).getBody();
    }
}