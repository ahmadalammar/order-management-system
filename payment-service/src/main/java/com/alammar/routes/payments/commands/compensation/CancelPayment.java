package com.alammar.routes.payments.commands.compensation;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CancelPayment extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:cancelPayment?brokers=localhost:9092")
            .routeId("CancelPayment")
            .process(exchange -> {
                log.warn("proceed cancel payment......");
            })
            .log("Payment Canceled successfully ${body}");
    }
}
