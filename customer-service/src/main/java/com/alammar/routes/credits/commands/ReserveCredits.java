package com.alammar.routes.credits.commands;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;

@Slf4j
@Component
public class ReserveCredits extends RouteBuilder {

    @Override
    public void configure() {

        // when unexpected exception occurred then drop in DLQ for further investigations...
        errorHandler(deadLetterChannel("log:deadLetterChannel?level=ERROR"));

        // when expected exception occurred then retry 3 times,
        // if still cant then proceed for cancel order and refund...
        onException(SocketTimeoutException.class)
            .maximumRedeliveries(3)
            .useExponentialBackOff()
            .backOffMultiplier(2)
            .log("UnExpected Exception on Reserve Credits : ${body}")
            .log("Proceed to cancel order and compensate")
            .to("kafka:cancelOrder?brokers=localhost:9092");

        from("kafka:reserveCredits?brokers=localhost:9092&maxPollIntervalMs=3000")
            .routeId("ReserveCredits")
            .saga()
            .propagation(SagaPropagation.MANDATORY)
            .compensation("direct:refundCredits")
            .process(exchange -> {
                // your business logic here...
                log.info("Processing reserve Credits....");

                //throw new NullPointerException("Unexpected error occurred!"); // will be handled by DLQ..

                //throw new SocketTimeoutException("Failed to connect to server!"); // will be handled by onException route..
            })
            .log("Credits ${body} reserved")
            .to("kafka:completeOrder?brokers=localhost:9092");
    }
}
