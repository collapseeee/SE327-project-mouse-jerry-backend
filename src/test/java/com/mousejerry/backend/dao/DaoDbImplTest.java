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

    @Mock
    private com.mousejerry.backend.repository.CategoryRepository categoryRepository;

    @Mock
    private com.mousejerry.backend.repository.GameRepository gameRepository;

    @InjectMocks
    private UserDaoDbImpl userDao;

    @InjectMocks
    private OrderDaoDbImpl orderDao;

    @InjectMocks
    private com.mousejerry.backend.dao.impl.CategoryDaoDbImpl categoryDao;

    @InjectMocks
    private com.mousejerry.backend.dao.impl.GameDaoDbImpl gameDao;

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

    @Test
    void testCategoryDaoDelegation() {
        com.mousejerry.backend.entity.Category cat = com.mousejerry.backend.entity.Category.builder().id(1L).name("Fighting").slug("fighting").build();

        when(categoryRepository.save(cat)).thenReturn(cat);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(categoryRepository.findByNameIgnoreCase("Fighting")).thenReturn(Optional.of(cat));
        when(categoryRepository.findBySlugIgnoreCase("fighting")).thenReturn(Optional.of(cat));
        when(categoryRepository.findByIsVisibleTrueOrderByNameAsc()).thenReturn(List.of(cat));
        when(categoryRepository.findAll()).thenReturn(List.of(cat));

        assertSame(cat, categoryDao.save(cat));
        assertTrue(categoryDao.findById(1L).isPresent());
        assertTrue(categoryDao.findByName("Fighting").isPresent());
        assertTrue(categoryDao.findBySlug("fighting").isPresent());
        assertEquals(1, categoryDao.findAllVisible().size());
        assertEquals(1, categoryDao.findAll().size());

        verify(categoryRepository).save(cat);
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).findByNameIgnoreCase("Fighting");
        verify(categoryRepository).findBySlugIgnoreCase("fighting");
        verify(categoryRepository).findByIsVisibleTrueOrderByNameAsc();
        verify(categoryRepository).findAll();
    }

    @Test
    void testGameDaoDelegation() {
        com.mousejerry.backend.entity.Game game = com.mousejerry.backend.entity.Game.builder().id("clone-drone").name("Clone Drone").build();
        org.springframework.data.domain.Page<com.mousejerry.backend.entity.Game> page = new org.springframework.data.domain.PageImpl<>(List.of(game));

        when(gameRepository.save(game)).thenReturn(game);
        when(gameRepository.findById("clone-drone")).thenReturn(Optional.of(game));
        when(gameRepository.findByIsHighlightTrue()).thenReturn(List.of(game));
        when(gameRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        assertSame(game, gameDao.save(game));
        assertTrue(gameDao.findById("clone-drone").isPresent());
        assertEquals(1, gameDao.findHighlights().size());
        assertEquals(1, gameDao.findGames("Fighting", "drone", org.springframework.data.domain.Pageable.unpaged()).getTotalElements());

        verify(gameRepository).save(game);
        verify(gameRepository).findById("clone-drone");
        verify(gameRepository).findByIsHighlightTrue();
        verify(gameRepository).findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class));
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void testBuildSpecificationWithFilters() {
        org.springframework.data.jpa.domain.Specification<com.mousejerry.backend.entity.Game> spec =
                gameDao.buildSpecification("Fighting", "drone");
        assertNotNull(spec);

        jakarta.persistence.criteria.Root<com.mousejerry.backend.entity.Game> root = mock(jakarta.persistence.criteria.Root.class);
        jakarta.persistence.criteria.CriteriaQuery query = mock(jakarta.persistence.criteria.CriteriaQuery.class);
        jakarta.persistence.criteria.CriteriaBuilder cb = mock(jakarta.persistence.criteria.CriteriaBuilder.class);

        jakarta.persistence.criteria.Join primaryCatJoin = mock(jakarta.persistence.criteria.Join.class);
        jakarta.persistence.criteria.Join catJoin = mock(jakarta.persistence.criteria.Join.class);
        jakarta.persistence.criteria.Join searchCatJoin = mock(jakarta.persistence.criteria.Join.class);

        when(root.join("primaryCategory", jakarta.persistence.criteria.JoinType.LEFT)).thenReturn(primaryCatJoin);
        when(root.join("categories", jakarta.persistence.criteria.JoinType.LEFT)).thenReturn(catJoin).thenReturn(searchCatJoin);

        jakarta.persistence.criteria.Path namePath = mock(jakarta.persistence.criteria.Path.class);
        when(primaryCatJoin.get("name")).thenReturn(namePath);
        when(primaryCatJoin.get("slug")).thenReturn(namePath);
        when(catJoin.get("name")).thenReturn(namePath);
        when(catJoin.get("slug")).thenReturn(namePath);
        when(searchCatJoin.get("name")).thenReturn(namePath);
        when(root.get("name")).thenReturn(namePath);

        jakarta.persistence.criteria.Expression lowerExpr = mock(jakarta.persistence.criteria.Expression.class);
        when(cb.lower(any())).thenReturn(lowerExpr);

        jakarta.persistence.criteria.Predicate pred = mock(jakarta.persistence.criteria.Predicate.class);
        when(cb.equal(any(), anyString())).thenReturn(pred);
        when(cb.like(any(), anyString())).thenReturn(pred);
        when(cb.or(any(), any(), any(), any())).thenReturn(pred);
        when(cb.or(any(), any())).thenReturn(pred);
        when(cb.and(any(jakarta.persistence.criteria.Predicate[].class))).thenReturn(pred);

        jakarta.persistence.criteria.Predicate result = spec.toPredicate(root, query, cb);
        assertNotNull(result);
        verify(query).distinct(true);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void testBuildSpecificationEmptyFilters() {
        org.springframework.data.jpa.domain.Specification<com.mousejerry.backend.entity.Game> spec =
                gameDao.buildSpecification(null, "");
        assertNotNull(spec);

        jakarta.persistence.criteria.Root<com.mousejerry.backend.entity.Game> root = mock(jakarta.persistence.criteria.Root.class);
        jakarta.persistence.criteria.CriteriaBuilder cb = mock(jakarta.persistence.criteria.CriteriaBuilder.class);

        jakarta.persistence.criteria.Predicate pred = mock(jakarta.persistence.criteria.Predicate.class);
        when(cb.and(any(jakarta.persistence.criteria.Predicate[].class))).thenReturn(pred);

        jakarta.persistence.criteria.Predicate result = spec.toPredicate(root, null, cb);
        assertNotNull(result);
    }
}
