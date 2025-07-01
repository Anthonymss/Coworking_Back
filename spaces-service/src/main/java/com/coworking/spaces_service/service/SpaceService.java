package com.coworking.spaces_service.service;

import com.coworking.spaces_service.dto.SpaceDto;
import com.coworking.spaces_service.dto.SpaceResponseDto;

import java.util.List;
import java.util.Map;

public interface SpaceService {
    public Map<String, Object> getFilteredSpaces(String city, String district, String type, int page, int size);
    public SpaceDto getSpaceById(Long id);
    public SpaceResponseDto getInfoSpace(Long id);

    Map<String,List<String>> getListFilterSpace();
}
