package com.alammar.routes.order.commands.compensation;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Component
public class CancelOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:cancelOrder?brokers=localhost:9092")
            .routeId("CancelOrder")
            .saga()
            .propagation(SagaPropagation.MANDATORY)
            .to("saga:compensate")
            .log("Order ${body} Canceled");
    }
}
