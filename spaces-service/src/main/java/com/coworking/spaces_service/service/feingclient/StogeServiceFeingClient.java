package com.coworking.spaces_service.service.feingclient;

import com.coworking.spaces_service.configuration.feigh.CombinedFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "storage-service", configuration = CombinedFeignConfig.class)
public interface StogeServiceFeingClient {
    @PostMapping(value = "/upload/spaces", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file);
}
