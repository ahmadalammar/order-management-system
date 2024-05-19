package com.alammar.routes;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessPayment extends RouteBuilder {

    @Override
    public void configure() {

        onException(Exception.class)
            .log("UnExpected Exception during process payment : ${body}")
            .log("Proceed to compensate...")
            .to("saga:compensate");

        from("kafka:processPayment?brokers=localhost:9092")
            .routeId("ProcessPayment")
            .saga()
            .propagation(SagaPropagation.MANDATORY)
            .compensation("kafka:cancelPayment?brokers=localhost:9092")
            .process(exchange -> {
                // your business logic here...
                //throw new RuntimeException("Error during process payment...");
                log.info("Processing payment (connect with payment gateway)....");

            })
            .log("Payment processed successfully ${body}")
            .to("kafka:completeOrder?brokers=localhost:9092");
    }
}
