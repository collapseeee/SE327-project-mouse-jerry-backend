package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.CartDTO;

public interface CartService {

    CartDTO getCart(String username);

    CartDTO addToCart(String username, String gameId, int quantity);

    CartDTO updateCartItem(String username, String gameId, int quantity);

    CartDTO removeFromCart(String username, String gameId);

    void clearCart(String username);
}
