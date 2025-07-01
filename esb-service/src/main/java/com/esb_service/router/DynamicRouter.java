    package com.esb_service.router;

    import org.apache.camel.Exchange;
    import org.apache.camel.builder.RouteBuilder;
    import org.springframework.stereotype.Component;
    import org.springframework.cloud.client.discovery.DiscoveryClient;
    import org.springframework.cloud.client.ServiceInstance;

    import java.util.List;

    @Component
    public class DynamicRouter extends RouteBuilder {
        private final DiscoveryClient discoveryClient;

        public DynamicRouter(DiscoveryClient discoveryClient) {
            this.discoveryClient = discoveryClient;
        }
        //@Autowired
        //private UserInterceptor userInterceptor;

        @Override
        public void configure() throws Exception {

            //interceptSendToEndpoint("http://localhost:8081")
            //        .process(userInterceptor)
            //        .end();
            from("servlet:/?matchOnUriPrefix=true")
                    .routeId("dynamic-router")
                    .process(exchange -> {
                        String service = exchange.getIn().getHeader("X-Service-Name", String.class);
                        String path = exchange.getIn().getHeader("CamelHttpPath", String.class);
                        String method = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);

                        if (service == null || service.isBlank()) {
                            throw new IllegalArgumentException("❌ Header 'X-Service-Name' no especificado.");
                        }

                        if (path == null) path = "";

                        log.info("🔍 Request entrante - Servicio: {}, Path: {}, Method: {}", service, path, method);

                        // Buscar instancias del servicio en Eureka
                        List<ServiceInstance> instances = discoveryClient.getInstances(service);
                        if (instances == null || instances.isEmpty()) {
                            throw new IllegalArgumentException("❌ No se encontraron instancias para el servicio: " + service);
                        }

                        // Usar la primera instancia disponible
                        ServiceInstance instance = instances.get(0);
                        String baseUrl = instance.getUri().toString(); // http://localhost:8081
                        System.out.println("base url : "+baseUrl);
                        String fullUri = baseUrl;

                        exchange.getIn().setHeader("CamelHttpUri", fullUri);
                        exchange.getIn().setHeader(Exchange.HTTP_HOST, instance.getHost());
                        String authHeader = exchange.getIn().getHeader("Authorization", String.class);
                        if (authHeader != null) {
                            exchange.getIn().setHeader("Authorization", authHeader);
                        }

                        log.info("✅ Redirigiendo a URI descubierta desde Eureka: {}", fullUri);
                    })
                    .toD("${header.CamelHttpUri}?bridgeEndpoint=true&throwExceptionOnFailure=true");

        }
    }
