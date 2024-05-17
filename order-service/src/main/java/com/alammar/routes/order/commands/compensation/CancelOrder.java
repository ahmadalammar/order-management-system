package com.alammar.routes.order.commands.compensation;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CancelOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:cancelOrder?brokers=localhost:9092")
            .routeId("CancelOrder")
            .process(exchange -> {
                log.warn("process cancel order here...");
                // mark the order as canceled from DB and proceed to notify the user...
            })
            .log("Order ${body} Canceled");
    }
}
