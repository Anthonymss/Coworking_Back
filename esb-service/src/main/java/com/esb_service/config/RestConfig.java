package com.esb_service.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends RouteBuilder {
    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .contextPath("/route");

    }
}