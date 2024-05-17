package com.alammar.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.alammar.dto.OrderDto;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping
    public String testCreateOrder(OrderDto orderDto) {
        try {
            producerTemplate.sendBody("kafka:createOrder?brokers=localhost:9092", orderDto);
            return "Successfully Submitted Order for processing....";
        } catch (Exception e) {
            return "Error ." + e.getMessage();
        }
    }
}
