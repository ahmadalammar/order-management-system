package com.alammar.routes.order.commands;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Component
public class CompleteOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:completeOrder?brokers=localhost:9092")
            .routeId("CompleteOrder")
            .saga()
            .propagation(SagaPropagation.MANDATORY)
            .to("saga:complete")
            .log("Order ${body} Completed Successfully! sending notification to user...");
    }
}
