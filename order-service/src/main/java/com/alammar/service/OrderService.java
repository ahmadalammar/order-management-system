package com.alammar.service;

import java.util.Optional;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alammar.dto.OrderDto;
import com.alammar.model.Order;
import com.alammar.repo.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(OrderDto orderDto) {
        Order order = Order.builder()
            .id(orderDto.getId())
            .itemName(orderDto.getItemName())
            .status("PENDING")
            .build();
        orderRepository.save(order);
    }

    public void updateOrderStatus(String id, String status) {
        Order order = Order.builder()
            .id(id)
            .status(status)
            .build();
        orderRepository.save(order);
    }

    public OrderDto getOrder(String id) {
        return orderRepository.findById(id)
            .map(order -> OrderDto.builder()
                .id(order.getId())
                .itemName(order.getItemName())
                .status(order.getStatus())
                .build())
            .orElseThrow();
    }
}
