package com.alammar.routes;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.alammar.dto.OrderDto;

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
