package com.alammar.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alammar.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {
}
