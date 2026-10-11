package com.mousejerry.backend.dao;

import com.mousejerry.backend.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUserIdOrderByPurchaseDateDesc(Long userId);

    Optional<Order> findByOrderNumber(String orderNumber);
}
