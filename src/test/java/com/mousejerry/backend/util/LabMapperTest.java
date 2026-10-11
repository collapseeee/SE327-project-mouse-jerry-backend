package com.mousejerry.backend.util;

import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.OrderItemKeyDTO;
import com.mousejerry.backend.dto.UserProfileDTO;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.OrderItemKey;
import com.mousejerry.backend.entity.Role;
import com.mousejerry.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LabMapperTest {

    private LabMapper labMapper;

    @BeforeEach
    void setUp() {
        labMapper = new LabMapperImpl();
    }

    @Test
    void testToUserProfileDTO() {
        assertNull(labMapper.toUserProfileDTO(null));

        User user = User.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .role(Role.ROLE_CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        UserProfileDTO dto = labMapper.toUserProfileDTO(user);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("john", dto.getUsername());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals(Role.ROLE_CUSTOMER, dto.getRole());
    }

    @Test
    void testToOrderDTO() {
        assertNull(labMapper.toOrderDTO(null));

        OrderItemKey key = OrderItemKey.builder()
                .id(10L)
                .gameTitle("Cyberpunk")
                .gameKey("KEY-123")
                .price(BigDecimal.valueOf(59.99))
                .build();

        Order order = Order.builder()
                .id(100L)
                .orderNumber("ORD-100")
                .totalAmount(BigDecimal.valueOf(59.99))
                .purchaseDate(LocalDateTime.now())
                .items(List.of(key))
                .build();

        OrderDTO dto = labMapper.toOrderDTO(order);
        assertNotNull(dto);
        assertEquals(100L, dto.getId());
        assertEquals("ORD-100", dto.getOrderNumber());
        assertEquals(BigDecimal.valueOf(59.99), dto.getTotalAmount());
        assertEquals(1, dto.getItems().size());
        assertEquals("KEY-123", dto.getItems().get(0).getGameKey());
    }

    @Test
    void testToOrderDTOList() {
        assertNull(labMapper.toOrderDTOList(null));

        Order order = Order.builder().id(1L).orderNumber("ORD-1").build();
        List<OrderDTO> list = labMapper.toOrderDTOList(List.of(order));

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("ORD-1", list.get(0).getOrderNumber());
    }

    @Test
    void testToOrderItemKeyDTO() {
        assertNull(labMapper.toOrderItemKeyDTO(null));

        OrderItemKey key = OrderItemKey.builder()
                .id(5L)
                .gameTitle("Witcher 3")
                .gameKey("W3-KEY")
                .price(BigDecimal.valueOf(29.99))
                .build();

        OrderItemKeyDTO dto = labMapper.toOrderItemKeyDTO(key);
        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("Witcher 3", dto.getGameTitle());
        assertEquals("W3-KEY", dto.getGameKey());
    }

    @Test
    void testToOrderItemKeyDTOList() {
        assertNull(labMapper.toOrderItemKeyDTOList(null));

        OrderItemKey key = OrderItemKey.builder().id(1L).gameKey("K1").build();
        List<OrderItemKeyDTO> list = labMapper.toOrderItemKeyDTOList(List.of(key));

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("K1", list.get(0).getGameKey());
    }
}
