package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.CheckoutRequest;
import com.mousejerry.backend.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO checkout(String username, CheckoutRequest request);

    List<OrderDTO> getOrderHistory(String username);

    OrderDTO getOrderByNumber(String username, String orderNumber);
}
