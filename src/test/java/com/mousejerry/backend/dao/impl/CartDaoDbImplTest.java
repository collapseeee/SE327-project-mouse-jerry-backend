package com.mousejerry.backend.dao.impl;

import com.mousejerry.backend.entity.CartItem;
import com.mousejerry.backend.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartDaoDbImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartDaoDbImpl cartDao;

    private CartItem mockCartItem;

    @BeforeEach
    void setUp() {
        mockCartItem = CartItem.builder()
                .id(1L)
                .quantity(2)
                .build();
    }

    @Test
    void testFindByUserId() {
        when(cartItemRepository.findByUserIdOrderByCreatedAtAsc(10L)).thenReturn(List.of(mockCartItem));

        List<CartItem> result = cartDao.findByUserId(10L);

        assertEquals(1, result.size());
        assertEquals(mockCartItem, result.get(0));
        verify(cartItemRepository).findByUserIdOrderByCreatedAtAsc(10L);
    }

    @Test
    void testFindByUserIdAndGameId() {
        when(cartItemRepository.findByUserIdAndGameId(10L, "game-1")).thenReturn(Optional.of(mockCartItem));

        Optional<CartItem> result = cartDao.findByUserIdAndGameId(10L, "game-1");

        assertTrue(result.isPresent());
        assertEquals(mockCartItem, result.get());
        verify(cartItemRepository).findByUserIdAndGameId(10L, "game-1");
    }

    @Test
    void testSave() {
        when(cartItemRepository.save(mockCartItem)).thenReturn(mockCartItem);

        CartItem saved = cartDao.save(mockCartItem);

        assertNotNull(saved);
        assertEquals(mockCartItem, saved);
        verify(cartItemRepository).save(mockCartItem);
    }

    @Test
    void testDeleteByUserIdAndGameId() {
        doNothing().when(cartItemRepository).deleteByUserIdAndGameId(10L, "game-1");

        cartDao.deleteByUserIdAndGameId(10L, "game-1");

        verify(cartItemRepository).deleteByUserIdAndGameId(10L, "game-1");
    }

    @Test
    void testDeleteByUserId() {
        doNothing().when(cartItemRepository).deleteByUserId(10L);

        cartDao.deleteByUserId(10L);

        verify(cartItemRepository).deleteByUserId(10L);
    }

    @Test
    void testCountByUserId() {
        when(cartItemRepository.countByUserId(10L)).thenReturn(5L);

        long count = cartDao.countByUserId(10L);

        assertEquals(5L, count);
        verify(cartItemRepository).countByUserId(10L);
    }
}
