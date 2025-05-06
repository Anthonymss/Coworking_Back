package com.coworking.management_user.service.feignclient;

import com.coworking.management_user.configuration.feigh.CombinedFeignConfig;
import com.coworking.management_user.configuration.feigh.FeignClientConfig;
import com.coworking.management_user.configuration.feigh.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "storage-service", configuration = CombinedFeignConfig.class)
public interface StorageServiceFeignClient {
    @PostMapping(value = "/upload/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file);
}
