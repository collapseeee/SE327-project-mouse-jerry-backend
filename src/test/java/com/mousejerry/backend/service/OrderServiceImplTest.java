package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CartDao;
import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.dao.OrderDao;
import com.mousejerry.backend.dao.UserDao;
import com.mousejerry.backend.dto.CheckoutRequest;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.OrderItemKeyDTO;
import com.mousejerry.backend.entity.CartItem;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.util.LabMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private CartDao cartDao;

    @Mock
    private GameDao gameDao;

    @Mock
    private UserDao userDao;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User mockUser;
    private Game mockGame;
    private CartItem mockCartItem;
    private Order mockOrder;
    private OrderDTO mockOrderDTO;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id(1L)
                .username("testuser")
                .build();

        mockGame = Game.builder()
                .id("battlefield-3")
                .name("Battlefield 3")
                .price(BigDecimal.valueOf(1969.00))
                .coverImage("bf3.jpg")
                .build();

        mockCartItem = CartItem.builder()
                .id(10L)
                .user(mockUser)
                .game(mockGame)
                .quantity(2)
                .build();

        mockOrder = Order.builder()
                .id(100L)
                .orderNumber("ORD-12345678")
                .user(mockUser)
                .totalAmount(BigDecimal.valueOf(3938.00))
                .purchaseDate(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();

        mockOrderDTO = OrderDTO.builder()
                .id(100L)
                .orderNumber("ORD-12345678")
                .userId(1L)
                .username("testuser")
                .totalAmount(BigDecimal.valueOf(3938.00))
                .items(List.of(
                        OrderItemKeyDTO.builder().gameTitle("Battlefield 3").gameKey("KEY1-KEY2-KEY3").build(),
                        OrderItemKeyDTO.builder().gameTitle("Battlefield 3").gameKey("KEY4-KEY5-KEY6").build()
                ))
                .build();
    }

    @Test
    void testCheckoutFromCartSuccess() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(mockCartItem));
        when(orderDao.save(any(Order.class))).thenReturn(mockOrder);
        when(labMapper.toOrderDTO(mockOrder)).thenReturn(mockOrderDTO);

        OrderDTO result = orderService.checkout("testuser", null);

        assertNotNull(result);
        assertEquals("ORD-12345678", result.getOrderNumber());
        verify(orderDao).save(any(Order.class));
        verify(cartDao).deleteByUserId(1L);
    }

    @Test
    void testCheckoutEmptyCartThrows() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(Collections.emptyList());

        assertThrows(IllegalStateException.class, () -> orderService.checkout("testuser", null));
        verify(orderDao, never()).save(any(Order.class));
    }

    @Test
    void testCheckoutSingleItemSuccess() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(gameDao.findById("battlefield-3")).thenReturn(Optional.of(mockGame));
        when(orderDao.save(any(Order.class))).thenReturn(mockOrder);
        when(labMapper.toOrderDTO(mockOrder)).thenReturn(mockOrderDTO);

        CheckoutRequest request = CheckoutRequest.builder()
                .checkoutType("single")
                .gameId("battlefield-3")
                .quantity(2)
                .build();

        OrderDTO result = orderService.checkout("testuser", request);

        assertNotNull(result);
        verify(orderDao).save(any(Order.class));
        // Ensure cart is NOT cleared on single checkout
        verify(cartDao, never()).deleteByUserId(anyLong());
    }

    @Test
    void testCheckoutSingleItemBlankGameIdThrows() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        CheckoutRequest request = CheckoutRequest.builder()
                .checkoutType("single")
                .gameId(" ")
                .build();

        assertThrows(IllegalArgumentException.class, () -> orderService.checkout("testuser", request));
    }

    @Test
    void testCheckoutSingleItemGameNotFoundThrows() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(gameDao.findById("non-existent")).thenReturn(Optional.empty());

        CheckoutRequest request = CheckoutRequest.builder()
                .checkoutType("single")
                .gameId("non-existent")
                .build();

        assertThrows(NoSuchElementException.class, () -> orderService.checkout("testuser", request));
    }

    @Test
    void testGetOrderHistory() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(orderDao.findByUserIdOrderByPurchaseDateDesc(1L)).thenReturn(List.of(mockOrder));
        when(labMapper.toOrderDTOList(List.of(mockOrder))).thenReturn(List.of(mockOrderDTO));

        List<OrderDTO> orders = orderService.getOrderHistory("testuser");

        assertEquals(1, orders.size());
        assertEquals("ORD-12345678", orders.get(0).getOrderNumber());
    }

    @Test
    void testGetOrderByNumberSuccess() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(orderDao.findByOrderNumber("ORD-12345678")).thenReturn(Optional.of(mockOrder));
        when(labMapper.toOrderDTO(mockOrder)).thenReturn(mockOrderDTO);

        OrderDTO result = orderService.getOrderByNumber("testuser", "ORD-12345678");

        assertNotNull(result);
        assertEquals("ORD-12345678", result.getOrderNumber());
    }

    @Test
    void testGetOrderByNumberNotFound() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(orderDao.findByOrderNumber("ORD-UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.getOrderByNumber("testuser", "ORD-UNKNOWN"));
    }

    @Test
    void testGetOrderByNumberUnauthorizedUser() {
        User otherUser = User.builder().id(999L).username("other").build();
        Order otherOrder = Order.builder().orderNumber("ORD-OTHER").user(otherUser).build();

        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(orderDao.findByOrderNumber("ORD-OTHER")).thenReturn(Optional.of(otherOrder));

        assertThrows(IllegalArgumentException.class, () -> orderService.getOrderByNumber("testuser", "ORD-OTHER"));
    }

    @Test
    void testCheckoutSingleItemNullQuantityAndPriceDefaults() {
        Game zeroPriceGame = Game.builder().id("free-game").name("Free Game").price(null).build();
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(gameDao.findById("free-game")).thenReturn(Optional.of(zeroPriceGame));
        when(orderDao.save(any(Order.class))).thenReturn(mockOrder);
        when(labMapper.toOrderDTO(mockOrder)).thenReturn(mockOrderDTO);

        CheckoutRequest request = CheckoutRequest.builder()
                .checkoutType("single")
                .gameId("free-game")
                .quantity(null)
                .build();

        OrderDTO result = orderService.checkout("testuser", request);
        assertNotNull(result);
        verify(orderDao).save(any(Order.class));
    }

    @Test
    void testCheckoutCartWithNullFieldsInCartItem() {
        CartItem nullFieldItem = CartItem.builder()
                .id(99L)
                .user(mockUser)
                .game(null)
                .quantity(null)
                .build();

        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(nullFieldItem));
        when(orderDao.save(any(Order.class))).thenReturn(mockOrder);
        when(labMapper.toOrderDTO(mockOrder)).thenReturn(mockOrderDTO);

        CheckoutRequest request = CheckoutRequest.builder()
                .checkoutType("cart")
                .build();

        OrderDTO result = orderService.checkout("testuser", request);
        assertNotNull(result);
        verify(orderDao).save(any(Order.class));
    }
}
