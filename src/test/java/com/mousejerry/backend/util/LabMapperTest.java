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

    @Test
    void testToCategoryDTO() {
        assertNull(labMapper.toCategoryDTO(null));

        com.mousejerry.backend.entity.Category cat = com.mousejerry.backend.entity.Category.builder()
                .id(1L)
                .name("Fighting")
                .slug("fighting")
                .isVisible(true)
                .build();

        com.mousejerry.backend.dto.CategoryDTO dto = labMapper.toCategoryDTO(cat);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Fighting", dto.getName());
        assertEquals("fighting", dto.getSlug());
        assertTrue(dto.getIsVisible());
    }

    @Test
    void testToCategoryDTOList() {
        assertNull(labMapper.toCategoryDTOList(null));

        com.mousejerry.backend.entity.Category cat = com.mousejerry.backend.entity.Category.builder().id(1L).name("Fighting").build();
        List<com.mousejerry.backend.dto.CategoryDTO> list = labMapper.toCategoryDTOList(List.of(cat));

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Fighting", list.get(0).getName());
    }

    @Test
    void testToGameDTO() {
        assertNull(labMapper.toGameDTO(null));

        com.mousejerry.backend.entity.Category cat1 = com.mousejerry.backend.entity.Category.builder().id(1L).name("Fighting").build();
        com.mousejerry.backend.entity.Category cat2 = com.mousejerry.backend.entity.Category.builder().id(2L).name("Action").build();

        com.mousejerry.backend.entity.Game game = com.mousejerry.backend.entity.Game.builder()
                .id("clone-drone")
                .name("Clone Drone")
                .price(BigDecimal.valueOf(1702))
                .coverImage("http://cover.jpg")
                .description("Combat game")
                .publisher("Doborog")
                .releaseDate("2021-07-27")
                .stock(12)
                .isHighlight(true)
                .primaryCategory(cat1)
                .categories(new java.util.HashSet<>(List.of(cat1, cat2)))
                .build();

        com.mousejerry.backend.entity.GameScreenshot shot = com.mousejerry.backend.entity.GameScreenshot.builder()
                .id(10L)
                .game(game)
                .url("http://shot.jpg")
                .displayOrder(0)
                .build();
        game.setScreenshots(List.of(shot));

        com.mousejerry.backend.dto.GameDTO dto = labMapper.toGameDTO(game);
        assertNotNull(dto);
        assertEquals("clone-drone", dto.getId());
        assertEquals("Clone Drone", dto.getName());
        assertEquals("Fighting", dto.getCategory());
        assertTrue(dto.getCategories().contains("Fighting"));
        assertTrue(dto.getCategories().contains("Action"));
        assertEquals(List.of("http://shot.jpg"), dto.getScreenshots());
        assertTrue(dto.getIsHighlight());
    }

    @Test
    void testToGameDTO_WithNullAssociations() {
        com.mousejerry.backend.entity.Game game = com.mousejerry.backend.entity.Game.builder()
                .id("minimal-game")
                .name("Minimal Game")
                .price(BigDecimal.valueOf(100))
                .primaryCategory(null)
                .categories(null)
                .screenshots(null)
                .build();

        com.mousejerry.backend.dto.GameDTO dto = labMapper.toGameDTO(game);
        assertNotNull(dto);
        assertEquals("minimal-game", dto.getId());
        assertNull(dto.getCategory());
        assertTrue(dto.getCategories().isEmpty());
        assertTrue(dto.getScreenshots().isEmpty());
    }

    @Test
    void testToGameDTOList() {
        assertNull(labMapper.toGameDTOList(null));

        com.mousejerry.backend.entity.Game game = com.mousejerry.backend.entity.Game.builder().id("g1").name("Game 1").build();
        List<com.mousejerry.backend.dto.GameDTO> list = labMapper.toGameDTOList(List.of(game));

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("g1", list.get(0).getId());
    }
}
