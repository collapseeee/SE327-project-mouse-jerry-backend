package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CartDao;
import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.dao.UserDao;
import com.mousejerry.backend.dto.CartDTO;
import com.mousejerry.backend.dto.CartItemDTO;
import com.mousejerry.backend.entity.CartItem;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final GameDao gameDao;
    private final UserDao userDao;
    private final LabMapper labMapper;

    @Override
    public CartDTO getCart(String username) {
        User user = getUserOrThrow(username);
        List<CartItem> items = cartDao.findByUserId(user.getId());
        List<CartItemDTO> itemDTOs = labMapper.toCartItemDTOList(items);

        int totalItems = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemDTO dto : itemDTOs) {
            int qty = dto.getQuantity() != null ? dto.getQuantity() : 0;
            totalItems += qty;
            if (dto.getTotalPrice() != null) {
                totalAmount = totalAmount.add(dto.getTotalPrice());
            }
        }

        return CartDTO.builder()
                .items(itemDTOs)
                .totalItems(totalItems)
                .totalAmount(totalAmount)
                .build();
    }

    @Override
    @Transactional
    public CartDTO addToCart(String username, String gameId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        User user = getUserOrThrow(username);
        Game game = gameDao.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("Game not found with id: " + gameId));

        Optional<CartItem> existing = cartDao.findByUserIdAndGameId(user.getId(), gameId);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setUpdatedAt(LocalDateTime.now(java.time.ZoneOffset.UTC));
            cartDao.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .user(user)
                    .game(game)
                    .quantity(quantity)
                    .build();
            cartDao.save(newItem);
        }

        return getCart(username);
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(String username, String gameId, int quantity) {
        User user = getUserOrThrow(username);
        if (quantity <= 0) {
            cartDao.deleteByUserIdAndGameId(user.getId(), gameId);
            return getCart(username);
        }

        CartItem item = cartDao.findByUserIdAndGameId(user.getId(), gameId)
                .orElseThrow(() -> new NoSuchElementException("Cart item not found for game: " + gameId));

        item.setQuantity(quantity);
        item.setUpdatedAt(LocalDateTime.now(java.time.ZoneOffset.UTC));
        cartDao.save(item);

        return getCart(username);
    }

    @Override
    @Transactional
    public CartDTO removeFromCart(String username, String gameId) {
        User user = getUserOrThrow(username);
        cartDao.deleteByUserIdAndGameId(user.getId(), gameId);
        return getCart(username);
    }

    @Override
    @Transactional
    public void clearCart(String username) {
        User user = getUserOrThrow(username);
        cartDao.deleteByUserId(user.getId());
    }

    private User getUserOrThrow(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }
}
