package com.coworking.spaces_service.presentation.controller;

import com.coworking.spaces_service.presentation.dto.SpaceDto;
import com.coworking.spaces_service.presentation.dto.SpaceResponseDto;
import com.coworking.spaces_service.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spaces")
public class SpaceController {
    private final SpaceService spaceService;

    @GetMapping
    public ResponseEntity<List<SpaceDto>> getSpaces(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String spaceType) {
        System.out.println("getConsulta:  "+city+"  "+district   +"   "+spaceType);
        String cityParam = (city != null && !city.isEmpty()) ? city : null;
        String districtParam = (district != null && !district.isEmpty()) ? district : null;
        String typeParam = (spaceType != null && !spaceType.isEmpty()) ? spaceType : null;

        return new ResponseEntity<>(spaceService.getFilteredSpaces(cityParam, districtParam, typeParam), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceDto> getSpaceById(@PathVariable Long id) {
        SpaceDto spaceDto = spaceService.getSpaceById(id);
        return new ResponseEntity<>(spaceDto, HttpStatus.OK);
    }
    @GetMapping("/price/{id}")
    public ResponseEntity<SpaceResponseDto> getSpacePriceById(@PathVariable Long id) {
        SpaceResponseDto spaceResponseDto = spaceService.getInfoSpace(id);
        return new ResponseEntity<>(spaceResponseDto, HttpStatus.OK);
    }
    @GetMapping("/filters")
    public ResponseEntity<Map<String,List<String>>> getListFiltro(){
        return new ResponseEntity<>(spaceService.getListFilterSpace(),HttpStatus.OK);
    }


}
