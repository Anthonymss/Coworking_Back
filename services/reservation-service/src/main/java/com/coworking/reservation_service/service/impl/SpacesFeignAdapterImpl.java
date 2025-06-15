package com.coworking.reservation_service.service.impl;

import com.coworking.reservation_service.dto.SpaceResponseDto;
import com.coworking.reservation_service.service.SpacesFeignAdapter;
import com.coworking.reservation_service.service.feignclient.EsbFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpacesFeignAdapterImpl implements SpacesFeignAdapter {
    private final EsbFeignClient esbFeignClient;

    @Override
    public SpaceResponseDto getSpaceInfo(Long spaceId) {
        return esbFeignClient.getInfoSpace(spaceId,"spaces-service").getBody();
    }
}