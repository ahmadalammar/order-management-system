package com.alammar.routes.order.commands;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException()
            .log("UnExpected Exception Happened on Processing Order : ${body}")
            .log("Proceed to compensate")
            .to("kafka:cancelOrder?brokers=localhost:9092");

        from("kafka:processOrder?brokers=localhost:9092")
            .routeId("ProcessOrder")
            .saga()
            .propagation(SagaPropagation.MANDATORY)
            .compensation("direct:cancelProcessOrder")
            .process(exchange -> {
                log.info("Processing orderId: " + exchange.getIn().getBody());
            })
            .to("kafka:reserveCredits?brokers=localhost:9092");
    }
}
