package com.mousejerry.backend.dto;

import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.Highlight;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HighlightDtoTest {

    @Test
    void testHighlightDtoProperties() {
        LocalDateTime now = LocalDateTime.now();
        HighlightDTO dto = HighlightDTO.builder()
                .id(1L)
                .gameId("elden-ring")
                .gameName("Elden Ring")
                .customImage("https://img.test/banner.jpg")
                .buttonColor("#8b0000")
                .createdAt(now)
                .build();

        assertEquals(1L, dto.getId());
        assertEquals("elden-ring", dto.getGameId());
        assertEquals("Elden Ring", dto.getGameName());
        assertEquals("https://img.test/banner.jpg", dto.getCustomImage());
        assertEquals("#8b0000", dto.getButtonColor());
        assertEquals(now, dto.getCreatedAt());

        HighlightDTO empty = new HighlightDTO();
        empty.setId(2L);
        empty.setGameId("g2");
        empty.setGameName("Game 2");
        empty.setCustomImage("img2");
        empty.setButtonColor("#000");
        empty.setCreatedAt(now);

        assertEquals(2L, empty.getId());
        assertEquals("g2", empty.getGameId());
        assertEquals("Game 2", empty.getGameName());
        assertEquals("img2", empty.getCustomImage());
        assertEquals("#000", empty.getButtonColor());
        assertEquals(now, empty.getCreatedAt());
    }

    @Test
    void testCreateHighlightRequestProperties() {
        CreateHighlightRequest req = CreateHighlightRequest.builder()
                .gameId("elden-ring")
                .customImage("img")
                .buttonColor("#8b0000")
                .build();

        assertEquals("elden-ring", req.getGameId());
        assertEquals("img", req.getCustomImage());
        assertEquals("#8b0000", req.getButtonColor());

        CreateHighlightRequest noArg = new CreateHighlightRequest();
        assertNull(noArg.getGameId());
    }

    @Test
    void testUpdateHighlightRequestProperties() {
        UpdateHighlightRequest req = UpdateHighlightRequest.builder()
                .gameId("sekiro")
                .customImage("img")
                .buttonColor("#fff")
                .build();

        assertEquals("sekiro", req.getGameId());
        assertEquals("img", req.getCustomImage());
        assertEquals("#fff", req.getButtonColor());

        UpdateHighlightRequest noArg = new UpdateHighlightRequest();
        assertNull(noArg.getGameId());
    }

    @Test
    void testHighlightEntityProperties() {
        Game game = Game.builder().id("elden-ring").name("Elden Ring").build();
        Highlight highlight = Highlight.builder()
                .id(1L)
                .game(game)
                .customImage("custom")
                .buttonColor("#8b0000")
                .build();

        assertEquals(1L, highlight.getId());
        assertEquals(game, highlight.getGame());
        assertEquals("custom", highlight.getCustomImage());
        assertEquals("#8b0000", highlight.getButtonColor());

        Highlight noArg = new Highlight();
        noArg.setId(2L);
        noArg.setGame(game);
        noArg.setCustomImage("c2");
        noArg.setButtonColor("#123");

        assertEquals(2L, noArg.getId());
        assertEquals(game, noArg.getGame());
        assertEquals("c2", noArg.getCustomImage());
        assertEquals("#123", noArg.getButtonColor());
    }
}
