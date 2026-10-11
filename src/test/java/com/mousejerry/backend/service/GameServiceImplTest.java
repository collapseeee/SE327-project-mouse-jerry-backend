package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.entity.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameDao gameDao;

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
}
