package com.coworking.reservation_service.service.feignclient;

import com.coworking.reservation_service.presentation.dto.SpaceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@FeignClient(name = "spaces-service")
public interface SpacesFeignClient {

    @GetMapping("/api/v1/spaces/price/{id}")
    ResponseEntity<SpaceResponseDto> getInfoSpace(@PathVariable Long id);

}