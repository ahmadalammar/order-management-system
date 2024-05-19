package com.alammar.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alammar.dto.OrderDto;
import com.alammar.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private ProducerTemplate producerTemplate;
    @Autowired
    private OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody OrderDto orderDto) {
        try {
            String json = new ObjectMapper().writeValueAsString(orderDto);
            producerTemplate.sendBody("kafka:createOrder?brokers=localhost:9092", json);
            return "Successfully Submitted Order for processing....";
        } catch (Exception e) {
            return "Error ." + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    public OrderDto getOrderDetails(@PathVariable String id) {
        return orderService.getOrder(id);
    }

}
