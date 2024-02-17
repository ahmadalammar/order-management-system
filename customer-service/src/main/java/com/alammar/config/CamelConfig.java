package com.alammar.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CamelConfig {

    @Value("${camel.service.lra.coordinator-url}")
    private String lraCoordinatorUrl;
    @Value("${camel.service.lra.local-participant-url}")
    private String lraLocalParticipantUrl;

    @Bean
    public CamelContext initCamelContext(List<RouteBuilder> routeBuilderList) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

        routeBuilderList.stream()
                .forEach(routeBuilder -> {
                    try {
                        camelContext.addRoutes(routeBuilder);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        org.apache.camel.service.lra.LRASagaService sagaService = new org.apache.camel.service.lra.LRASagaService();
        sagaService.setCoordinatorUrl(lraCoordinatorUrl);
        sagaService.setLocalParticipantUrl(lraLocalParticipantUrl);
        camelContext.addService(sagaService);
        camelContext.start();

        return camelContext;
    }

}
