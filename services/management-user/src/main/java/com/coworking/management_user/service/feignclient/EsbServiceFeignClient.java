package com.coworking.management_user.service.feignclient;

import com.coworking.management_user.configuration.feighConfig.FeignEncoder;
import com.coworking.management_user.dto.GoogleTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@FeignClient(name = "esb-service", configuration = FeignEncoder.class)
public interface EsbServiceFeignClient {
    @PostMapping("route/api/v1/auth/account/google")
    Map<String, String> getGoogleAccountInfo(
            @RequestHeader("X-Service-Name") String serviceName,
            @RequestBody GoogleTokenDto body
    );

    @PostMapping(value = "route/upload/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file, @RequestHeader("X-Service-Name") String serviceName);
}
