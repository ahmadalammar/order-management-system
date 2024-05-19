package com.alammar.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alammar.dto.OrderDto;
import com.alammar.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CancelOrder extends RouteBuilder {

    @Autowired
    private OrderService orderService;
    @Override
    public void configure() throws Exception {
        from("kafka:cancelOrder?brokers=localhost:9092")
            .routeId("CancelOrder")
            .log("canceling order route ${body}")
            .process(exchange -> {
                log.warn("process cancel order here...");
                String json = exchange.getIn().getBody(String.class);
                OrderDto orderDto = new ObjectMapper().readValue(json, OrderDto.class);
                orderService.updateOrderStatus(orderDto.getId(), "CANCELED");
            })
            .log("Order ${body} Canceled");
    }
}
