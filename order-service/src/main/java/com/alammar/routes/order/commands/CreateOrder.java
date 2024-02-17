package com.alammar.routes.order.commands;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaCompletionMode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrder extends RouteBuilder {

    @Override
    public void configure() {
        from("kafka:createOrder?brokers=localhost:9092")
            .routeId("CreateOrder")
            .log("Order Received ${body}")
            .saga()
            .completionMode(SagaCompletionMode.MANUAL)
            .to("kafka:processOrder?brokers=localhost:9092");
    }
}
