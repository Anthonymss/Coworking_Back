package com.coworking.spaces_service.controller;

import com.coworking.spaces_service.dto.SpaceDto;
import com.coworking.spaces_service.dto.SpaceResponseDto;
import com.coworking.spaces_service.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spaces")
public class SpaceController {
    private final SpaceService spaceService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSpaces(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String spaceType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = spaceService.getFilteredSpaces(city, district, spaceType, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    @PostMapping("/id-name")
    public ResponseEntity<List<String>> getAlllNameForSpaceByid(@RequestBody List<Long> listId){
        return new ResponseEntity<>(this.spaceService.listNamesForId(listId),HttpStatus.OK);
    }


}
