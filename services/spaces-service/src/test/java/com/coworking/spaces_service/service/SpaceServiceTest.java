package com.coworking.spaces_service.service;

import com.coworking.spaces_service.dto.SpaceDto;
import com.coworking.spaces_service.dto.SpaceResponseDto;
import com.coworking.spaces_service.entity.Site;
import com.coworking.spaces_service.entity.Space;
import com.coworking.spaces_service.exception.NotFoundSpace;
import com.coworking.spaces_service.repository.SpaceRepository;
import com.coworking.spaces_service.service.impl.SpaceServiceImpl;
import com.coworking.spaces_service.util.enums.SpaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpaceServiceImplTest {

    @Mock
    private SpaceRepository spaceRepository;

    @InjectMocks
    private SpaceServiceImpl spaceService;
    @Test
    void shouldReturnFilteredSpacesWithPagination() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Conference Room");
        space.setPricePerHour(new BigDecimal("100.00"));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Space> page = new PageImpl<>(List.of(space), pageable, 1);

        when(spaceRepository.findSpaces("City1", "District1", SpaceType.CONFERENCE_ROOM, pageable))
                .thenReturn(page);

        Page<SpaceDto> result = (Page<SpaceDto>) spaceService.getFilteredSpaces("City1", "District1", "CONFERENCE_ROOM", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Conference Room", result.getContent().get(0).getName());

        verify(spaceRepository, times(1)).findSpaces(
                eq("City1"),
                eq("District1"),
                eq(SpaceType.CONFERENCE_ROOM),
                any(Pageable.class)
        );
    }



    @Test
    void shouldThrowExceptionWhenSpaceNotFoundById() {
        when(spaceRepository.existsById(1L)).thenReturn(false);

        NotFoundSpace exception = assertThrows(NotFoundSpace.class, () -> spaceService.getSpaceById(1L));

        assertEquals("No space found with id 1", exception.getMessage());
    }

    @Test
    void shouldReturnSpaceById() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Private Office");
        space.setPricePerHour(new BigDecimal("150.00"));
        Site site = new Site();
        site.setName("Main Site");
        space.setSite(site);

        when(spaceRepository.existsById(1L)).thenReturn(true);
        when(spaceRepository.getReferenceById(1L)).thenReturn(space);

        SpaceDto result = spaceService.getSpaceById(1L);

        assertNotNull(result);
        assertEquals("Private Office", result.getName());
        assertEquals(new BigDecimal("150.00"), result.getPricePerHour());
    }

    @Test
    void shouldReturnInfoSpace() {
        Space space = new Space();
        space.setId(1L);
        space.setName("Shared Workspace");
        space.setPricePerHour(new BigDecimal("50.00"));
        Site site = new Site();
        site.setName("Downtown Location");
        site.setAddress("123 Main St");
        space.setSite(site);

        when(spaceRepository.existsById(1L)).thenReturn(true);
        when(spaceRepository.getReferenceById(1L)).thenReturn(space);

        SpaceResponseDto result = spaceService.getInfoSpace(1L);

        assertNotNull(result);
        assertEquals(new BigDecimal("50.00"), result.getPriceHour());
        assertEquals("Downtown Location;123 Main St;Shared Workspace", result.getSpaceDescription());
    }

    @Test
    void shouldReturnListFilterSpace() {
        when(spaceRepository.findDistinctCity()).thenReturn(List.of("City1", "City2"));
        when(spaceRepository.findDistinctDistrict()).thenReturn(List.of("District1", "District2"));

        Map<String, List<String>> result = spaceService.getListFilterSpace();

        assertNotNull(result);
        assertTrue(result.containsKey("city"));
        assertTrue(result.containsKey("district"));
        assertEquals(2, result.get("city").size());
        assertEquals(2, result.get("district").size());
    }
}
