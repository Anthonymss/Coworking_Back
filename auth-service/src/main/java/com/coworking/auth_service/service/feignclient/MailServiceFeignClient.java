package com.coworking.auth_service.service.feignclient;

import com.coworking.auth_service.configuration.feign.FeignClientConfig;
import com.coworking.auth_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "esb-service")
public interface MailServiceFeignClient {
    @PostMapping("route/api/v1/notifications/send")
    ResponseEntity<String> sendEmailForWelcome(
            @RequestHeader("X-Service-Name") String serviceName,
            @RequestParam String templateName,
            @RequestBody UserDto userDto
    );
}