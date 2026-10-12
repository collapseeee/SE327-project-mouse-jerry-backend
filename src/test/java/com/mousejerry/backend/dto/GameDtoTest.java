package com.mousejerry.backend.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameDtoTest {

    @Test
    void testCreateGameRequestProperties() {
        CreateGameRequest request = CreateGameRequest.builder()
                .id("game-1")
                .name("Test Game")
                .price(BigDecimal.valueOf(19.99))
                .description("Description")
                .coverImage("cover.jpg")
                .publisher("Publisher")
                .releaseDate("2026-10-10")
                .stock(10)
                .isHighlight(true)
                .primaryCategoryId(1L)
                .category("Action")
                .categoryIds(Set.of(1L, 2L))
                .categories(List.of("Action", "Adventure"))
                .screenshots(List.of("shot1.png", "shot2.png"))
                .build();

        assertEquals("game-1", request.getId());
        assertEquals("Test Game", request.getName());
        assertEquals(BigDecimal.valueOf(19.99), request.getPrice());
        assertEquals("Description", request.getDescription());
        assertEquals("cover.jpg", request.getCoverImage());
        assertEquals("Publisher", request.getPublisher());
        assertEquals("2026-10-10", request.getReleaseDate());
        assertEquals(10, request.getStock());
        assertTrue(request.getIsHighlight());
        assertEquals(1L, request.getPrimaryCategoryId());
        assertEquals("Action", request.getCategory());
        assertEquals(2, request.getCategoryIds().size());
        assertEquals(2, request.getCategories().size());
        assertEquals(2, request.getScreenshots().size());

        CreateGameRequest noArg = new CreateGameRequest();
        assertNull(noArg.getId());
    }

    @Test
    void testUpdateGameRequestProperties() {
        UpdateGameRequest request = UpdateGameRequest.builder()
                .name("Updated Game")
                .price(BigDecimal.valueOf(29.99))
                .description("Updated")
                .coverImage("updated.jpg")
                .publisher("New Publisher")
                .releaseDate("2026-11-11")
                .stock(20)
                .isHighlight(false)
                .primaryCategoryId(2L)
                .category("RPG")
                .categoryIds(Set.of(2L))
                .categories(List.of("RPG"))
                .screenshots(List.of("shot.png"))
                .build();

        assertEquals("Updated Game", request.getName());
        assertEquals(BigDecimal.valueOf(29.99), request.getPrice());
        assertEquals("Updated", request.getDescription());
        assertEquals("updated.jpg", request.getCoverImage());
        assertEquals("New Publisher", request.getPublisher());
        assertEquals("2026-11-11", request.getReleaseDate());
        assertEquals(20, request.getStock());
        assertFalse(request.getIsHighlight());
        assertEquals(2L, request.getPrimaryCategoryId());
        assertEquals("RPG", request.getCategory());
        assertEquals(1, request.getCategoryIds().size());
        assertEquals(1, request.getCategories().size());
        assertEquals(1, request.getScreenshots().size());

        UpdateGameRequest noArg = new UpdateGameRequest();
        assertNull(noArg.getName());
    }

    @Test
    void testGameHighlightRequestProperties() {
        GameHighlightRequest request = GameHighlightRequest.builder()
                .isHighlight(true)
                .build();

        assertTrue(request.getIsHighlight());

        GameHighlightRequest noArg = new GameHighlightRequest();
        assertNull(noArg.getIsHighlight());
    }

    @Test
    void testStorageFileDtoProperties() {
        StorageFileDTO dto = StorageFileDTO.builder()
                .name("games/image.png")
                .publicUrl("https://example.com/games/image.png")
                .build();

        assertEquals("games/image.png", dto.getName());
        assertEquals("https://example.com/games/image.png", dto.getPublicUrl());

        StorageFileDTO noArg = new StorageFileDTO();
        assertNull(noArg.getName());
    }

    @Test
    void testPresignedUrlDtoProperties() {
        PresignedUrlDTO dto = PresignedUrlDTO.builder()
                .uploadUrl("https://example.com/upload")
                .fileKey("games/test.png")
                .publicUrl("https://example.com/games/test.png")
                .build();

        assertEquals("https://example.com/upload", dto.getUploadUrl());
        assertEquals("games/test.png", dto.getFileKey());
        assertEquals("https://example.com/games/test.png", dto.getPublicUrl());

        PresignedUrlDTO noArg = new PresignedUrlDTO();
        assertNull(noArg.getUploadUrl());
    }
}
