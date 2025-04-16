package com.coworking.reservation_service.service;

import com.coworking.reservation_service.presentation.dto.SpaceResponseDto;

public interface SpacesFeignAdapter {
    SpaceResponseDto getSpaceInfo(Long spaceId);
}
