package com.coworking.spaces_service.service.impl;

import com.coworking.spaces_service.exception.NotFoundSpace;
import com.coworking.spaces_service.entity.Space;
import com.coworking.spaces_service.repository.EquipmentRepository;
import com.coworking.spaces_service.repository.SpaceEquipmentRepository;
import com.coworking.spaces_service.repository.SpaceRepository;
import com.coworking.spaces_service.dto.EquipmentDto;
import com.coworking.spaces_service.dto.SpaceDto;
import com.coworking.spaces_service.dto.SpaceResponseDto;
import com.coworking.spaces_service.service.SpaceService;
import com.coworking.spaces_service.util.enums.SpaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final SpaceEquipmentRepository spaceEquipmentRepository;
    private final EquipmentRepository equipmentRepository;
    private final Map<String, Map<String, Object>> spacesCache = new ConcurrentHashMap<>();//Flyweight cache
    private final Map<String, List<String>> filterCache = new ConcurrentHashMap<>();
    @Override
    public Map<String, Object> getFilteredSpaces(String city, String district, String type, int page, int size) {
        String cacheKey = generateCacheKey(city, district, type) + "_page_" + page + "_size_" + size;

        if (spacesCache.containsKey(cacheKey)) {
            System.out.println("Se cargo desde el cache ...");
            return (Map<String, Object>) spacesCache.get(cacheKey);
        }

        SpaceType spaceType = null;
        if (type != null && !type.isEmpty()) {
            spaceType = parseSpaceType(type);
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Space> spacePage = spaceRepository.findSpaces(city, district, spaceType, pageable);

        List<SpaceDto> spaceDtos = spacePage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("spaces", spaceDtos);
        response.put("totalItems", spacePage.getTotalElements());
        response.put("totalPages", spacePage.getTotalPages());
        response.put("currentPage", spacePage.getNumber());

        spacesCache.put(cacheKey,  response);

        return response;
    }


    @Override
    public SpaceDto getSpaceById(Long id) {
        if(!spaceRepository.existsById(id)) {
            throw new NotFoundSpace("No space found with id " + id);
        }
        return convertToDto(spaceRepository.getReferenceById(id));
    }

    @Override
    public SpaceResponseDto getInfoSpace(Long id) {
        if(!spaceRepository.existsById(id)) {
            throw new NotFoundSpace("No space found with id " + id);
        }
        Space space=spaceRepository.getReferenceById(id);
        SpaceResponseDto spaceResponseDto=SpaceResponseDto.builder()
                .priceHour(space.getPricePerHour())
                .spaceDescription(space.getSite().getName()+";"+space.getSite().getAddress()+";"+space.getName())
                .build();
        return spaceResponseDto ;
    }

    @Override
    public Map<String, List<String>> getListFilterSpace() {
        filterCache.computeIfAbsent("city", key ->fetchDistinctCities());
        filterCache.computeIfAbsent("district", key -> fetchDistinctDistricts());
        filterCache.computeIfAbsent("type", key -> fetchSpaceTypes());
        return new HashMap<>(filterCache);
    }

    private String generateCacheKey(String city, String district, String type) {
        return String.join("_", city == null ? "" : city, district == null ? "" : district, type == null ? "" : type);
    }

    private SpaceType parseSpaceType(String type) {
        try {
            return SpaceType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid spaceType provided: " + type);
            return null;
        }
    }
    private SpaceDto convertToDto(Space space) {
        String siteName =space.getSite().getCity()+" - "+space.getSite().getDistrict() ;
        List<EquipmentDto> equipmentDtoList = space.getSpaceEquipments().stream()
                .map(equipment -> EquipmentDto.builder()
                        .id(equipment.getEquipment().getId())
                        .name(equipment.getEquipment().getName())
                        .description(equipment.getEquipment().getDescription())
                        .quantity(equipment.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return SpaceDto.builder()
                .id(space.getId())
                .name(space.getName())
                .description(space.getDescription())
                .capacity(space.getCapacity())
                .pricePerHour(space.getPricePerHour())
                .urlImage(space.getUrlImage())
                .siteName(siteName)
                .address(space.getSite().getAddress())
                .spaceType(space.getSpaceType().name())
                .ListEquipment(equipmentDtoList)
                .build();
    }
    public void updateCache() {
        this.spacesCache.clear();
    }
    private List<String> fetchDistinctCities() {
        return Optional.ofNullable(spaceRepository.findDistinctCity())
                .orElse(List.of());
    }

    private List<String> fetchDistinctDistricts() {
        return Optional.ofNullable(spaceRepository.findDistinctDistrict())
                .orElse(List.of());
    }

    private List<String> fetchSpaceTypes() {
        return Arrays.stream(SpaceType.values())
                .map(SpaceType::name)
                .collect(Collectors.toList());
    }
    public void invalidateCache() {
        filterCache.clear();
    }
}
