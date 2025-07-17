package com.mercado.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MercadoPagoProperties {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
