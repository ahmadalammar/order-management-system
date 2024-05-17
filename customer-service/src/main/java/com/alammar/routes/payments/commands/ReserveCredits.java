package com.alammar.routes.payments.commands;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReserveCredits extends RouteBuilder {

    @Override
    public void configure() {

        onException(Exception.class)
            .log("UnExpected Exception on Reserve Credits : ${body}")
            .log("Proceed to compensate...")
            .to("saga:compensate");

        from("kafka:reserveCredits?brokers=localhost:9092")
            .routeId("ReserveCredits")
            .saga()
            .propagation(SagaPropagation.MANDATORY)
            .compensation("kafka:refundCredits?brokers=localhost:9092")
            .process(exchange -> {
                // your business logic here...
                log.info("Processing reserve Credits....");

            })
            .log("Credits ${body} reserved")
            .to("kafka:processPayment?brokers=localhost:9092");
    }
}
