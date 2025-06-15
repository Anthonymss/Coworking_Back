/*
package com.example.esb_v2.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserInterceptor implements Processor {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String method = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
        String path = exchange.getIn().getHeader("CamelHttpPath", String.class);

        // Solo si es POST al path exacto /users
        if ("POST".equalsIgnoreCase(method) && "/users".equals(path)) {
            String body = exchange.getIn().getBody(String.class);
            System.out.println("Interceptor ejecutado. METHOD: " + method + ", PATH: " + path + ", BODY: " + body);

            Map<String, Object> map = mapper.readValue(body, Map.class);
            map.put("rol", "admin");
            String out = mapper.writeValueAsString(map);
            exchange.getIn().setBody(out);
            System.out.println("🔧 Interceptor USER POST modificado: " + out);
        } else {
            System.out.println("⛔ Interceptor ignorado. METHOD: " + method + ", PATH: " + path);
        }
    }
}




* */