package com.coworking.management_user.configuration.feighConfig;

import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignMultipartSupportConfig {

    @Bean
    public SpringFormEncoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
