package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CartDao;
import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.dao.OrderDao;
import com.mousejerry.backend.dao.UserDao;
import com.mousejerry.backend.dto.CheckoutRequest;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.entity.CartItem;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.OrderItemKey;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.util.GameKeyGenerator;
import com.mousejerry.backend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final CartDao cartDao;
    private final GameDao gameDao;
    private final UserDao userDao;
    private final LabMapper labMapper;

    @Override
    @Transactional
    public OrderDTO checkout(String username, CheckoutRequest request) {
        User user = getUserOrThrow(username);

        if (request != null && "single".equalsIgnoreCase(request.getCheckoutType())) {
            return processSingleCheckout(user, request);
        }

        return processCartCheckout(user);
    }

    @Override
    public List<OrderDTO> getOrderHistory(String username) {
        User user = getUserOrThrow(username);
        List<Order> orders = orderDao.findByUserIdOrderByPurchaseDateDesc(user.getId());
        return labMapper.toOrderDTOList(orders);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderDao.findAllOrderByPurchaseDateDesc();
        return labMapper.toOrderDTOList(orders);
    }

    @Override
    public OrderDTO getOrderByNumber(String username, String orderNumber) {
        User user = getUserOrThrow(username);
        Order order = orderDao.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new NoSuchElementException("Order not found with order number: " + orderNumber));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("User not authorized to access this order");
        }

        return labMapper.toOrderDTO(order);
    }

    private OrderDTO processSingleCheckout(User user, CheckoutRequest request) {
        if (request.getGameId() == null || request.getGameId().trim().isEmpty()) {
            throw new IllegalArgumentException("Game ID is required for single item checkout");
        }

        Game game = gameDao.findById(request.getGameId().trim())
                .orElseThrow(() -> new NoSuchElementException("Game not found with id: " + request.getGameId()));

        int qty = request.getQuantity() != null && request.getQuantity() > 0 ? request.getQuantity() : 1;
        BigDecimal unitPrice = game.getPrice() != null ? game.getPrice() : BigDecimal.ZERO;
        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(qty));

        Order order = Order.builder()
                .orderNumber(GameKeyGenerator.generateOrderNumber())
                .user(user)
                .totalAmount(totalAmount)
                .purchaseDate(LocalDateTime.now(java.time.ZoneOffset.UTC))
                .items(new ArrayList<>())
                .build();

        for (int i = 0; i < qty; i++) {
            OrderItemKey itemKey = OrderItemKey.builder()
                    .order(order)
                    .gameId(game.getId())
                    .gameTitle(game.getName())
                    .coverImage(game.getCoverImage())
                    .gameKey(GameKeyGenerator.generateKey())
                    .price(unitPrice)
                    .build();
            order.getItems().add(itemKey);
        }

        Order saved = orderDao.save(order);
        return labMapper.toOrderDTO(saved);
    }

    private OrderDTO processCartCheckout(User user) {
        List<CartItem> cartItems = cartDao.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot checkout with an empty cart");
        }

        Order order = Order.builder()
                .orderNumber(GameKeyGenerator.generateOrderNumber())
                .user(user)
                .totalAmount(calculateTotalAmount(cartItems))
                .purchaseDate(LocalDateTime.now(java.time.ZoneOffset.UTC))
                .items(new ArrayList<>())
                .build();

        order.getItems().addAll(createOrderItemKeys(order, cartItems));

        Order saved = orderDao.save(order);
        cartDao.deleteByUserId(user.getId());
        return labMapper.toOrderDTO(saved);
    }

    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            BigDecimal price = resolveItemPrice(item);
            int qty = resolveItemQuantity(item);
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(qty)));
        }
        return totalAmount;
    }

    private List<OrderItemKey> createOrderItemKeys(Order order, List<CartItem> cartItems) {
        List<OrderItemKey> itemKeys = new ArrayList<>();
        for (CartItem item : cartItems) {
            itemKeys.addAll(createKeysForCartItem(order, item));
        }
        return itemKeys;
    }

    private List<OrderItemKey> createKeysForCartItem(Order order, CartItem item) {
        List<OrderItemKey> keys = new ArrayList<>();
        Game game = item != null ? item.getGame() : null;
        BigDecimal price = resolveItemPrice(item);
        String gameId = game != null ? game.getId() : null;
        String gameTitle = (game != null && game.getName() != null) ? game.getName() : "Unknown Game";
        String coverImage = game != null ? game.getCoverImage() : null;
        int qty = resolveItemQuantity(item);

        for (int i = 0; i < qty; i++) {
            keys.add(OrderItemKey.builder()
                    .order(order)
                    .gameId(gameId)
                    .gameTitle(gameTitle)
                    .coverImage(coverImage)
                    .gameKey(GameKeyGenerator.generateKey())
                    .price(price)
                    .build());
        }
        return keys;
    }

    private BigDecimal resolveItemPrice(CartItem item) {
        if (item != null && item.getGame() != null && item.getGame().getPrice() != null) {
            return item.getGame().getPrice();
        }
        return BigDecimal.ZERO;
    }

    private int resolveItemQuantity(CartItem item) {
        return (item != null && item.getQuantity() != null) ? item.getQuantity() : 0;
    }

    private User getUserOrThrow(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }
}
