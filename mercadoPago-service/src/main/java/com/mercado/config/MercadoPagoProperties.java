package com.mercado.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoProperties {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
