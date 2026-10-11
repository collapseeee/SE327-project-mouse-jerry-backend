package com.mousejerry.backend.dao.impl;

import com.mousejerry.backend.dao.OrderDao;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class OrderDaoDbImpl implements OrderDao {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserIdOrderByPurchaseDateDesc(Long userId) {
        return orderRepository.findByUserIdOrderByPurchaseDateDesc(userId);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
}
