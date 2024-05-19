package com.alammar.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alammar.dto.OrderDto;
import com.alammar.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CompleteOrder extends RouteBuilder {

    @Autowired
    private OrderService orderService;
    @Override
    public void configure() throws Exception {
        from("kafka:completeOrder?brokers=localhost:9092")
            .routeId("CompleteOrder")
            .log("complete order route ${body}")
            .saga()
            .propagation(SagaPropagation.MANDATORY)
            .process(exchange -> {
                String json = exchange.getIn().getBody(String.class);
                OrderDto orderDto = new ObjectMapper().readValue(json, OrderDto.class);
                orderService.updateOrderStatus(orderDto.getId(), "SUCCESS");
            })
            .to("saga:complete")
            .log("Order ${body} Completed Successfully! sending notification to user...");
    }
}
