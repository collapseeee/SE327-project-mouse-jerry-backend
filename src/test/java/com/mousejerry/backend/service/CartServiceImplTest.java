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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartDao cartDao;

    @Mock
    private GameDao gameDao;

    @Mock
    private UserDao userDao;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private User mockUser;
    private Game mockGame;
    private CartItem mockCartItem;
    private CartItemDTO mockCartItemDTO;

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
                .stock(10)
                .build();

        mockCartItem = CartItem.builder()
                .id(100L)
                .user(mockUser)
                .game(mockGame)
                .quantity(2)
                .build();

        mockCartItemDTO = CartItemDTO.builder()
                .id(100L)
                .gameId("battlefield-3")
                .gameTitle("Battlefield 3")
                .coverImage("bf3.jpg")
                .unitPrice(BigDecimal.valueOf(1969.00))
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(3938.00))
                .stock(10)
                .build();
    }

    @Test
    void testGetCartSuccess() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(mockCartItem));
        when(labMapper.toCartItemDTOList(List.of(mockCartItem))).thenReturn(List.of(mockCartItemDTO));

        CartDTO cart = cartService.getCart("testuser");

        assertNotNull(cart);
        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(3938.00), cart.getTotalAmount());
    }

    @Test
    void testGetCartEmpty() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(Collections.emptyList());
        when(labMapper.toCartItemDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        CartDTO cart = cartService.getCart("testuser");

        assertNotNull(cart);
        assertEquals(0, cart.getItems().size());
        assertEquals(0, cart.getTotalItems());
        assertEquals(BigDecimal.ZERO, cart.getTotalAmount());
    }

    @Test
    void testGetCartUserNotFound() {
        when(userDao.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cartService.getCart("unknown"));
    }

    @Test
    void testAddToCartNewItem() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(gameDao.findById("battlefield-3")).thenReturn(Optional.of(mockGame));
        when(cartDao.findByUserIdAndGameId(1L, "battlefield-3")).thenReturn(Optional.empty());
        when(cartDao.findByUserId(1L)).thenReturn(List.of(mockCartItem));
        when(labMapper.toCartItemDTOList(List.of(mockCartItem))).thenReturn(List.of(mockCartItemDTO));

        CartDTO result = cartService.addToCart("testuser", "battlefield-3", 2);

        assertNotNull(result);
        verify(cartDao).save(any(CartItem.class));
    }

    @Test
    void testAddToCartExistingItemIncrementsQuantity() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(gameDao.findById("battlefield-3")).thenReturn(Optional.of(mockGame));
        when(cartDao.findByUserIdAndGameId(1L, "battlefield-3")).thenReturn(Optional.of(mockCartItem));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(mockCartItem));
        when(labMapper.toCartItemDTOList(List.of(mockCartItem))).thenReturn(List.of(mockCartItemDTO));

        CartDTO result = cartService.addToCart("testuser", "battlefield-3", 1);

        assertNotNull(result);
        assertEquals(3, mockCartItem.getQuantity());
        verify(cartDao).save(mockCartItem);
    }

    @Test
    void testAddToCartInvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> cartService.addToCart("testuser", "battlefield-3", 0));
    }

    @Test
    void testAddToCartGameNotFound() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(gameDao.findById("unknown-game")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cartService.addToCart("testuser", "unknown-game", 1));
    }

    @Test
    void testUpdateCartItemSuccess() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserIdAndGameId(1L, "battlefield-3")).thenReturn(Optional.of(mockCartItem));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(mockCartItem));
        when(labMapper.toCartItemDTOList(List.of(mockCartItem))).thenReturn(List.of(mockCartItemDTO));

        CartDTO result = cartService.updateCartItem("testuser", "battlefield-3", 5);

        assertNotNull(result);
        assertEquals(5, mockCartItem.getQuantity());
        verify(cartDao).save(mockCartItem);
    }

    @Test
    void testUpdateCartItemZeroRemovesItem() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(Collections.emptyList());
        when(labMapper.toCartItemDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        CartDTO result = cartService.updateCartItem("testuser", "battlefield-3", 0);

        assertNotNull(result);
        verify(cartDao).deleteByUserIdAndGameId(1L, "battlefield-3");
    }

    @Test
    void testUpdateCartItemNotFound() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserIdAndGameId(1L, "non-existing")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cartService.updateCartItem("testuser", "non-existing", 3));
    }

    @Test
    void testRemoveFromCart() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(Collections.emptyList());
        when(labMapper.toCartItemDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        CartDTO result = cartService.removeFromCart("testuser", "battlefield-3");

        assertNotNull(result);
        verify(cartDao).deleteByUserIdAndGameId(1L, "battlefield-3");
    }

    @Test
    void testClearCart() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        cartService.clearCart("testuser");

        verify(cartDao).deleteByUserId(1L);
    }

    @Test
    void testGetCartWithNullFieldDTOs() {
        CartItemDTO nullFieldDTO = CartItemDTO.builder().quantity(null).totalPrice(null).build();
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(mockCartItem));
        when(labMapper.toCartItemDTOList(List.of(mockCartItem))).thenReturn(List.of(nullFieldDTO));

        CartDTO cart = cartService.getCart("testuser");
        assertNotNull(cart);
        assertEquals(0, cart.getTotalItems());
        assertEquals(BigDecimal.ZERO, cart.getTotalAmount());
    }
}
