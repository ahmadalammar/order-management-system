package com.alammar.routes;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaCompletionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alammar.dto.OrderDto;
import com.alammar.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class CreateOrder extends RouteBuilder {
    @Autowired
    private OrderService orderService;
    @Override
    public void configure() {

        onException(Exception.class).log("UnExpected Exception Happened on Creating Order : ${body}")
            .log("Proceed to compensate")
            .to("saga:compensate");

        from("kafka:createOrder?brokers=localhost:9092")
            .routeId("CreateOrder")
            .log("Order Received ${body}")
            .saga()
            .completionMode(SagaCompletionMode.MANUAL)
            .compensation("kafka:cancelOrder?brokers=localhost:9092")
            .process(exchange -> {

                String json = exchange.getIn().getBody(String.class);
                OrderDto orderDto = new ObjectMapper().readValue(json,OrderDto.class);

                orderService.createOrder(orderDto);

                // TODO add the business logic here
                log.info("processing create order here...");
                // once processing create order completed successfully it will send
                // a kafka topic command to reserveCredits..

                // if any exception occurred during processing create order here
                // then onException(Exception.class) will catch the error
                // and initiate saga:compensate to trigger the compensation
            })
            .to("kafka:reserveCredits?brokers=localhost:9092");
    }
}
