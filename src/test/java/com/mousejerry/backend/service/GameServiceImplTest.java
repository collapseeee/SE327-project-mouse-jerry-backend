package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CategoryDao;
import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.dto.CreateGameRequest;
import com.mousejerry.backend.dto.UpdateGameRequest;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.entity.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameDao gameDao;

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    void getGames_ReturnsPage() {
        Game game = Game.builder().id("clone-drone").name("Clone Drone").build();
        Page<Game> page = new PageImpl<>(List.of(game));
        when(gameDao.findGames("Fighting", "drone", Pageable.unpaged())).thenReturn(page);

        Page<Game> result = gameService.getGames("Fighting", "drone", Pageable.unpaged());
        assertEquals(1, result.getTotalElements());
        assertEquals("clone-drone", result.getContent().get(0).getId());
    }

    @Test
    void getGameById_Found_ReturnsGame() {
        Game game = Game.builder().id("clone-drone").name("Clone Drone").build();
        when(gameDao.findById("clone-drone")).thenReturn(Optional.of(game));

        Game result = gameService.getGameById("clone-drone");
        assertNotNull(result);
        assertEquals("clone-drone", result.getId());
    }

    @Test
    void getGameById_NotFound_ThrowsException() {
        when(gameDao.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> gameService.getGameById("unknown"));
    }

    @Test
    void getHighlightedGames_ReturnsList() {
        Game game = Game.builder().id("battlefield-3").name("Battlefield 3").isHighlight(true).build();
        when(gameDao.findHighlights()).thenReturn(List.of(game));

        List<Game> result = gameService.getHighlightedGames();
        assertEquals(1, result.size());
        assertTrue(result.get(0).getIsHighlight());
    }

    @Test
    void createGame_WithCustomId_Success() {
        CreateGameRequest request = CreateGameRequest.builder()
                .id("custom-game-id")
                .name("Custom Game")
                .price(BigDecimal.valueOf(19.99))
                .stock(10)
                .isHighlight(true)
                .build();

        when(gameDao.existsById("custom-game-id")).thenReturn(false);
        when(gameDao.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game created = gameService.createGame(request);

        assertNotNull(created);
        assertEquals("custom-game-id", created.getId());
        assertEquals("Custom Game", created.getName());
        assertEquals(BigDecimal.valueOf(19.99), created.getPrice());
        assertTrue(created.getIsHighlight());
        assertEquals(10, created.getStock());
    }

    @Test
    void createGame_DuplicateCustomId_ThrowsException() {
        CreateGameRequest request = CreateGameRequest.builder()
                .id("existing-id")
                .name("Some Game")
                .price(BigDecimal.valueOf(19.99))
                .build();

        when(gameDao.existsById("existing-id")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> gameService.createGame(request));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void createGame_AutoSlug_SlugCollision_AppendsSuffix() {
        CreateGameRequest request = CreateGameRequest.builder()
                .name("Super Mario")
                .price(BigDecimal.valueOf(49.99))
                .build();

        when(gameDao.existsById("super-mario")).thenReturn(true);
        when(gameDao.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game created = gameService.createGame(request);

        assertNotNull(created);
        assertTrue(created.getId().startsWith("super-mario-"));
    }

    @Test
    void createGame_WithCategoriesAndScreenshots_Success() {
        Category action = Category.builder().id(1L).name("Action").slug("action").build();
        Category rpg = Category.builder().id(2L).name("RPG").slug("rpg").build();

        CreateGameRequest request = CreateGameRequest.builder()
                .name("Elden Ring")
                .price(BigDecimal.valueOf(59.99))
                .primaryCategoryId(1L)
                .categoryIds(Set.of(2L))
                .screenshots(List.of("https://img.com/shot1.png", "https://img.com/shot2.png"))
                .build();

        when(gameDao.existsById("elden-ring")).thenReturn(false);
        when(categoryDao.findById(1L)).thenReturn(Optional.of(action));
        when(categoryDao.findById(2L)).thenReturn(Optional.of(rpg));
        when(gameDao.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game created = gameService.createGame(request);

        assertNotNull(created);
        assertEquals("Action", created.getPrimaryCategory().getName());
        assertTrue(created.getCategories().contains(action));
        assertTrue(created.getCategories().contains(rpg));
        assertEquals(2, created.getScreenshots().size());
        assertEquals("https://img.com/shot1.png", created.getScreenshots().get(0).getUrl());
    }

    @Test
    void createGame_PrimaryCategoryNotFound_ThrowsException() {
        CreateGameRequest request = CreateGameRequest.builder()
                .name("Game")
                .price(BigDecimal.valueOf(10.0))
                .primaryCategoryId(999L)
                .build();

        when(gameDao.existsById(anyString())).thenReturn(false);
        when(categoryDao.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> gameService.createGame(request));
        assertTrue(ex.getMessage().contains("Primary category not found with id: 999"));
    }

    @Test
    void createGame_WithCategoryNameFallback_Success() {
        Category indie = Category.builder().id(3L).name("Indie").slug("indie").build();
        CreateGameRequest request = CreateGameRequest.builder()
                .name("Celeste")
                .price(BigDecimal.valueOf(19.99))
                .category("Indie")
                .categories(List.of("indie"))
                .build();

        when(gameDao.existsById("celeste")).thenReturn(false);
        when(categoryDao.findByName("Indie")).thenReturn(Optional.of(indie));
        when(categoryDao.findByName("indie")).thenReturn(Optional.empty());
        when(categoryDao.findBySlug("indie")).thenReturn(Optional.of(indie));
        when(gameDao.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game created = gameService.createGame(request);

        assertNotNull(created);
        assertEquals(indie, created.getPrimaryCategory());
    }

    @Test
    void createGame_InvalidValidation_ThrowsException() {
        CreateGameRequest nullRequest = null;
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(nullRequest));

        CreateGameRequest blankName = CreateGameRequest.builder().name("  ").price(BigDecimal.ONE).build();
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(blankName));

        CreateGameRequest nullPrice = CreateGameRequest.builder().name("Name").price(null).build();
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(nullPrice));

        CreateGameRequest negPrice = CreateGameRequest.builder().name("Name").price(BigDecimal.valueOf(-5)).build();
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(negPrice));
    }

    @Test
    void updateGame_Valid_Success() {
        Game existing = Game.builder()
                .id("game-1")
                .name("Old Name")
                .price(BigDecimal.valueOf(10.0))
                .screenshots(new ArrayList<>())
                .build();

        Category shooter = Category.builder().id(5L).name("Shooter").build();
        UpdateGameRequest request = UpdateGameRequest.builder()
                .name("New Name")
                .price(BigDecimal.valueOf(20.0))
                .description("Updated desc")
                .coverImage("cover.png")
                .publisher("Publisher")
                .releaseDate("2026-01-01")
                .stock(5)
                .isHighlight(true)
                .primaryCategoryId(5L)
                .screenshots(List.of("https://img.com/shot.png"))
                .build();

        when(gameDao.findById("game-1")).thenReturn(Optional.of(existing));
        when(categoryDao.findById(5L)).thenReturn(Optional.of(shooter));
        when(gameDao.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game updated = gameService.updateGame("game-1", request);

        assertEquals("New Name", updated.getName());
        assertEquals(BigDecimal.valueOf(20.0), updated.getPrice());
        assertEquals("Updated desc", updated.getDescription());
        assertEquals(5, updated.getStock());
        assertTrue(updated.getIsHighlight());
        assertEquals(shooter, updated.getPrimaryCategory());
        assertEquals(1, updated.getScreenshots().size());
    }

    @Test
    void updateGame_NotFound_ThrowsException() {
        UpdateGameRequest request = UpdateGameRequest.builder()
                .name("Valid Name")
                .price(BigDecimal.TEN)
                .build();

        when(gameDao.findById("non-existent")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> gameService.updateGame("non-existent", request));
    }

    @Test
    void updateGame_InvalidRequest_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame("id", null));

        UpdateGameRequest blankName = UpdateGameRequest.builder().name("").price(BigDecimal.TEN).build();
        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame("id", blankName));

        UpdateGameRequest negPrice = UpdateGameRequest.builder().name("Valid").price(BigDecimal.valueOf(-1)).build();
        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame("id", negPrice));
    }

    @Test
    void updateHighlight_ExplicitValue_Success() {
        Game game = Game.builder().id("game-1").isHighlight(false).build();
        when(gameDao.findById("game-1")).thenReturn(Optional.of(game));
        when(gameDao.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameService.updateHighlight("game-1", true);

        assertTrue(result.getIsHighlight());
    }

    @Test
    void updateHighlight_NullValue_TogglesValue() {
        Game game = Game.builder().id("game-1").isHighlight(true).build();
        when(gameDao.findById("game-1")).thenReturn(Optional.of(game));
        when(gameDao.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game result = gameService.updateHighlight("game-1", null);

        assertFalse(result.getIsHighlight());
    }

    @Test
    void updateHighlight_NotFound_ThrowsException() {
        when(gameDao.findById("missing")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> gameService.updateHighlight("missing", true));
    }

    @Test
    void deleteGame_Success() {
        Game game = Game.builder().id("game-1").build();
        when(gameDao.findById("game-1")).thenReturn(Optional.of(game));
        doNothing().when(gameDao).delete(game);

        gameService.deleteGame("game-1");

        verify(gameDao, times(1)).delete(game);
    }

    @Test
    void deleteGame_NotFound_ThrowsException() {
        when(gameDao.findById("missing")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> gameService.deleteGame("missing"));
    }
}
