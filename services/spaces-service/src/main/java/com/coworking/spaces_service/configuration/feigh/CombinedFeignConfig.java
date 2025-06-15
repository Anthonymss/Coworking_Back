package com.coworking.spaces_service.configuration.feigh;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ FeignClientConfig.class, FeignMultipartSupportConfig.class })
public class CombinedFeignConfig {
}
