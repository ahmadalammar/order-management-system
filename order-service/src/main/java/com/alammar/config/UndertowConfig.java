package com.alammar.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UndertowConfig extends RouteBuilder {
    @Value("${server.undertow.port}")
    private int undertowServerPort;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("undertow")
            .port(undertowServerPort)
            .contextPath("/server");
    }
}
