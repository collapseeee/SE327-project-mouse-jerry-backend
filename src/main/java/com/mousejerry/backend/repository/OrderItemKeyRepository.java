package com.mousejerry.backend.repository;

import com.mousejerry.backend.entity.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemKeyRepository extends JpaRepository<OrderItemKey, Long> {

    List<OrderItemKey> findByOrderId(Long orderId);
}
