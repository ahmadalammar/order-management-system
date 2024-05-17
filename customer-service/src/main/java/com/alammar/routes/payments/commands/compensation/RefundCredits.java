package com.alammar.routes.payments.commands.compensation;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RefundCredits extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:refundCredits?brokers=localhost:9092")
            .routeId("RefundCredits")
            .process(exchange -> {
                log.warn("proceed to refund credit to the user....");
            })
            .log("Credits ${body} refunded successfully!");
    }
}
