package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.GameDTO;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.service.GameService;
import com.mousejerry.backend.util.LabMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .setControllerAdvice(new com.mousejerry.backend.config.GlobalExceptionHandler())
                .build();
    }

    @Test
    void getGames_WithPaginationAndFilter_ReturnsPageWithHeader() throws Exception {
        Game game = Game.builder().id("clone-drone").name("Clone Drone").price(BigDecimal.valueOf(1702)).build();
        GameDTO dto = GameDTO.builder().id("clone-drone").name("Clone Drone").price(BigDecimal.valueOf(1702)).build();

        Page<Game> page = new PageImpl<>(List.of(game), Pageable.unpaged(), 1);
        when(gameService.getGames(eq("Fighting"), eq("drone"), any(Pageable.class))).thenReturn(page);
        when(labMapper.toGameDTOList(List.of(game))).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/games")
                        .param("category", "Fighting")
                        .param("search", "drone")
                        .param("page", "1")
                        .param("size", "10")
                        .param("direction", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", "1"))
                .andExpect(jsonPath("$[0].id").value("clone-drone"))
                .andExpect(jsonPath("$[0].name").value("Clone Drone"));
    }

    @Test
    void getGames_WithPageSizeExceedingMax_ClampsToMaxPageSize() throws Exception {
        when(gameService.getGames(any(), any(), any(Pageable.class))).thenReturn(Page.empty());
        when(labMapper.toGameDTOList(any())).thenReturn(List.of());

        mockMvc.perform(get("/api/games")
                        .param("size", "500")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(gameService).getGames(any(), any(), pageableCaptor.capture());
        assertEquals(GameController.MAX_PAGE_SIZE, pageableCaptor.getValue().getPageSize());
    }

    @Test
    void getGames_WithNegativePageSize_ClampsToOne() throws Exception {
        when(gameService.getGames(any(), any(), any(Pageable.class))).thenReturn(Page.empty());
        when(labMapper.toGameDTOList(any())).thenReturn(List.of());

        mockMvc.perform(get("/api/games")
                        .param("size", "-5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(gameService).getGames(any(), any(), pageableCaptor.capture());
        assertEquals(1, pageableCaptor.getValue().getPageSize());
    }

    @Test
    void getHighlights_ReturnsHighlightList() throws Exception {
        Game game = Game.builder().id("battlefield-3").name("Battlefield 3").isHighlight(true).build();
        GameDTO dto = GameDTO.builder().id("battlefield-3").name("Battlefield 3").isHighlight(true).build();

        when(gameService.getHighlightedGames()).thenReturn(List.of(game));
        when(labMapper.toGameDTOList(List.of(game))).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/games/highlights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("battlefield-3"));
    }

    @Test
    void getGameById_Found_ReturnsGame() throws Exception {
        Game game = Game.builder().id("clone-drone").name("Clone Drone").build();
        GameDTO dto = GameDTO.builder().id("clone-drone").name("Clone Drone").build();

        when(gameService.getGameById("clone-drone")).thenReturn(game);
        when(labMapper.toGameDTO(game)).thenReturn(dto);

        mockMvc.perform(get("/api/games/clone-drone")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("clone-drone"));
    }

    @Test
    void getGameById_NotFound_Returns404() throws Exception {
        when(gameService.getGameById("unknown")).thenThrow(new NoSuchElementException("Game not found with id: unknown"));

        mockMvc.perform(get("/api/games/unknown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Game not found with id: unknown"));
    }
}
