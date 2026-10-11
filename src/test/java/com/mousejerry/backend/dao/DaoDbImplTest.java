package com.mousejerry.backend.dao;

import com.mousejerry.backend.dao.impl.OrderDaoDbImpl;
import com.mousejerry.backend.dao.impl.UserDaoDbImpl;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.repository.OrderRepository;
import com.mousejerry.backend.repository.UserRepository;
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
class DaoDbImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UserDaoDbImpl userDao;

    @InjectMocks
    private OrderDaoDbImpl orderDao;

    @Test
    void testUserDaoDelegation() {
        User user = User.builder().id(1L).username("test").email("test@example.com").build();

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("test")).thenReturn(true);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertSame(user, userDao.save(user));
        assertTrue(userDao.findById(1L).isPresent());
        assertTrue(userDao.findByUsername("test").isPresent());
        assertTrue(userDao.findByEmail("test@example.com").isPresent());
        assertTrue(userDao.existsByUsername("test"));
        assertTrue(userDao.existsByEmail("test@example.com"));

        verify(userRepository).save(user);
        verify(userRepository).findById(1L);
        verify(userRepository).findByUsername("test");
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).existsByUsername("test");
        verify(userRepository).existsByEmail("test@example.com");
    }

    @Test
    void testOrderDaoDelegation() {
        Order order = Order.builder().id(2L).orderNumber("ORD-2").build();

        when(orderRepository.save(order)).thenReturn(order);
        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
        when(orderRepository.findByUserIdOrderByPurchaseDateDesc(1L)).thenReturn(List.of(order));
        when(orderRepository.findByOrderNumber("ORD-2")).thenReturn(Optional.of(order));

        assertSame(order, orderDao.save(order));
        assertTrue(orderDao.findById(2L).isPresent());
        assertEquals(1, orderDao.findByUserIdOrderByPurchaseDateDesc(1L).size());
        assertTrue(orderDao.findByOrderNumber("ORD-2").isPresent());

        verify(orderRepository).save(order);
        verify(orderRepository).findById(2L);
        verify(orderRepository).findByUserIdOrderByPurchaseDateDesc(1L);
        verify(orderRepository).findByOrderNumber("ORD-2");
    }
}
