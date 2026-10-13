package com.mousejerry.backend.dao.impl;

import com.mousejerry.backend.dao.CartDao;
import com.mousejerry.backend.entity.CartItem;
import com.mousejerry.backend.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class CartDaoDbImpl implements CartDao {

    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> findByUserId(Long userId) {
        return cartItemRepository.findByUserIdOrderByCreatedAtAsc(userId);
    }

    @Override
    public Optional<CartItem> findByUserIdAndGameId(Long userId, String gameId) {
        return cartItemRepository.findByUserIdAndGameId(userId, gameId);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteByUserIdAndGameId(Long userId, String gameId) {
        cartItemRepository.deleteByUserIdAndGameId(userId, gameId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    @Override
    public long countByUserId(Long userId) {
        return cartItemRepository.countByUserId(userId);
    }
}
