package com.coworking.management_user.service.feignclient;

import com.coworking.management_user.configuration.feigh.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
@FeignClient(name = "auth-service", configuration = FeignClientConfig.class)
public interface AuthServiceFeignClient {

    @PostMapping("/api/v1/auth/account/google")
    Map<String, String> getGoogleAccountInfo(@RequestBody Map<String, String> body);
}
