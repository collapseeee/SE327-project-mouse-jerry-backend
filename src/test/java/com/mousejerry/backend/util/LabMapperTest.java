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

        com.mousejerry.backend.entity.SystemRequirements req = com.mousejerry.backend.entity.SystemRequirements.builder()
                .id(1L)
                .os("Windows 10")
                .processor("Intel Core i7")
                .memory("16 GB")
                .graphics("RTX 2060")
                .storage("50 GB")
                .build();
        game.setSystemRequirements(req);

        com.mousejerry.backend.dto.GameDTO dto = labMapper.toGameDTO(game);
        assertNotNull(dto);
        assertEquals("clone-drone", dto.getId());
        assertEquals("Clone Drone", dto.getName());
        assertEquals("Fighting", dto.getCategory());
        assertTrue(dto.getCategories().contains("Fighting"));
        assertTrue(dto.getCategories().contains("Action"));
        assertEquals(List.of("http://shot.jpg"), dto.getScreenshots());
        assertTrue(dto.getIsHighlight());
        assertNotNull(dto.getSystemRequirements());
        assertEquals("Windows 10", dto.getSystemRequirements().getOs());
        assertEquals("Intel Core i7", dto.getSystemRequirements().getProcessor());
        assertEquals("16 GB", dto.getSystemRequirements().getMemory());
        assertEquals("RTX 2060", dto.getSystemRequirements().getGraphics());
        assertEquals("50 GB", dto.getSystemRequirements().getStorage());
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
                .systemRequirements(null)
                .build();

        com.mousejerry.backend.dto.GameDTO dto = labMapper.toGameDTO(game);
        assertNotNull(dto);
        assertEquals("minimal-game", dto.getId());
        assertNull(dto.getCategory());
        assertTrue(dto.getCategories().isEmpty());
        assertTrue(dto.getScreenshots().isEmpty());
        assertNull(dto.getSystemRequirements());
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

    @Test
    void testToCartItemDTO() {
        assertNull(labMapper.toCartItemDTO(null));

        com.mousejerry.backend.entity.Game game = com.mousejerry.backend.entity.Game.builder()
                .id("bf3")
                .name("Battlefield 3")
                .price(BigDecimal.valueOf(1969))
                .coverImage("bf3.jpg")
                .stock(5)
                .build();

        com.mousejerry.backend.entity.CartItem item = com.mousejerry.backend.entity.CartItem.builder()
                .id(1L)
                .game(game)
                .quantity(2)
                .build();

        com.mousejerry.backend.dto.CartItemDTO dto = labMapper.toCartItemDTO(item);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("bf3", dto.getGameId());
        assertEquals("Battlefield 3", dto.getGameTitle());
        assertEquals("bf3.jpg", dto.getCoverImage());
        assertEquals(BigDecimal.valueOf(1969), dto.getUnitPrice());
        assertEquals(2, dto.getQuantity());
        assertEquals(BigDecimal.valueOf(3938), dto.getTotalPrice());
        assertEquals(5, dto.getStock());
    }

    @Test
    void testToCartItemDTOList() {
        assertNull(labMapper.toCartItemDTOList(null));

        com.mousejerry.backend.entity.CartItem item = com.mousejerry.backend.entity.CartItem.builder().id(1L).build();
        List<com.mousejerry.backend.dto.CartItemDTO> list = labMapper.toCartItemDTOList(List.of(item));
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void testCalculateItemTotalPrice_EdgeCases() {
        assertEquals(BigDecimal.ZERO, labMapper.calculateItemTotalPrice(null));

        com.mousejerry.backend.entity.CartItem noGame = com.mousejerry.backend.entity.CartItem.builder().game(null).build();
        assertEquals(BigDecimal.ZERO, labMapper.calculateItemTotalPrice(noGame));

        com.mousejerry.backend.entity.Game noPriceGame = com.mousejerry.backend.entity.Game.builder().price(null).build();
        com.mousejerry.backend.entity.CartItem noPrice = com.mousejerry.backend.entity.CartItem.builder().game(noPriceGame).build();
        assertEquals(BigDecimal.ZERO, labMapper.calculateItemTotalPrice(noPrice));

        com.mousejerry.backend.entity.Game validGame = com.mousejerry.backend.entity.Game.builder().price(BigDecimal.valueOf(100)).build();
        com.mousejerry.backend.entity.CartItem nullQty = com.mousejerry.backend.entity.CartItem.builder().game(validGame).quantity(null).build();
        assertEquals(BigDecimal.ZERO, labMapper.calculateItemTotalPrice(nullQty));
    }

    @Test
    void testToHighlightDTO() {
        assertNull(labMapper.toHighlightDTO(null));

        com.mousejerry.backend.entity.Game game = com.mousejerry.backend.entity.Game.builder()
                .id("elden-ring")
                .name("Elden Ring")
                .build();
        com.mousejerry.backend.entity.Highlight highlight = com.mousejerry.backend.entity.Highlight.builder()
                .id(1L)
                .game(game)
                .customImage("https://img.test/banner.jpg")
                .buttonColor("#8b0000")
                .build();

        com.mousejerry.backend.dto.HighlightDTO dto = labMapper.toHighlightDTO(highlight);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("elden-ring", dto.getGameId());
        assertEquals("Elden Ring", dto.getGameName());
        assertEquals("https://img.test/banner.jpg", dto.getCustomImage());
        assertEquals("#8b0000", dto.getButtonColor());

        // Test with null game
        com.mousejerry.backend.entity.Highlight noGame = com.mousejerry.backend.entity.Highlight.builder().id(2L).build();
        com.mousejerry.backend.dto.HighlightDTO noGameDto = labMapper.toHighlightDTO(noGame);
        assertNotNull(noGameDto);
        assertNull(noGameDto.getGameId());
        assertNull(noGameDto.getGameName());
    }

    @Test
    void testToHighlightDTOList() {
        assertNull(labMapper.toHighlightDTOList(null));

        com.mousejerry.backend.entity.Highlight highlight = com.mousejerry.backend.entity.Highlight.builder().id(1L).build();
        List<com.mousejerry.backend.dto.HighlightDTO> list = labMapper.toHighlightDTOList(List.of(highlight));
        assertNotNull(list);
        assertEquals(1, list.size());
    }
}
