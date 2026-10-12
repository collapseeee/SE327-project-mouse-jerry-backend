package com.mousejerry.backend.dao;

import com.mousejerry.backend.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    List<CartItem> findByUserId(Long userId);

    Optional<CartItem> findByUserIdAndGameId(Long userId, String gameId);

    CartItem save(CartItem cartItem);

    void deleteByUserIdAndGameId(Long userId, String gameId);

    void deleteByUserId(Long userId);

    long countByUserId(Long userId);
}
