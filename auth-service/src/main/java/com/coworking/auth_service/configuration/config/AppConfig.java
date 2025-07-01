package com.coworking.auth_service.configuration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientIdGoogle;
    @Bean
    public String googleClientId() {
        return clientIdGoogle;
    }

}
