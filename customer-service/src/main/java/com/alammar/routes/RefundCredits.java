package com.alammar.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.alammar.dto.OrderDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RefundCredits extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:refundCredits?brokers=localhost:9092")
            .routeId("RefundCredits")
            .log("refunding credits route ${body}")
            .process(exchange -> {
                log.warn("proceed to refund credit to the user....");
            })
            .log("Credits ${body} refunded successfully!");
    }
}
