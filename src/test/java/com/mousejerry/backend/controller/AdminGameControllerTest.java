package com.mousejerry.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.CreateGameRequest;
import com.mousejerry.backend.dto.GameDTO;
import com.mousejerry.backend.dto.GameHighlightRequest;
import com.mousejerry.backend.dto.UpdateGameRequest;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.service.GameService;
import com.mousejerry.backend.util.LabMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminGameControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private AdminGameController adminGameController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminGameController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllGames_ReturnsListAndHeaders() throws Exception {
        Game game = Game.builder().id("elden-ring").name("Elden Ring").price(BigDecimal.valueOf(59.99)).build();
        GameDTO dto = GameDTO.builder().id("elden-ring").name("Elden Ring").price(BigDecimal.valueOf(59.99)).build();

        when(gameService.getGames(any(), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(game)));
        when(labMapper.toGameDTOList(List.of(game))).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/admin/games")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", "1"))
                .andExpect(jsonPath("$[0].id").value("elden-ring"))
                .andExpect(jsonPath("$[0].name").value("Elden Ring"));
    }

    @Test
    void getGameById_Success_ReturnsOk() throws Exception {
        Game game = Game.builder().id("hades").name("Hades").price(BigDecimal.valueOf(24.99)).build();
        GameDTO dto = GameDTO.builder().id("hades").name("Hades").price(BigDecimal.valueOf(24.99)).build();

        when(gameService.getGameById("hades")).thenReturn(game);
        when(labMapper.toGameDTO(game)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/admin/games/hades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("hades"))
                .andExpect(jsonPath("$.name").value("Hades"));
    }

    @Test
    void getGameById_NotFound_ReturnsNotFound() throws Exception {
        when(gameService.getGameById("unknown"))
                .thenThrow(new NoSuchElementException("Game not found with id: unknown"));

        mockMvc.perform(get("/api/v1/admin/games/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Game not found with id: unknown"));
    }

    @Test
    void createGame_Valid_ReturnsCreated() throws Exception {
        CreateGameRequest request = CreateGameRequest.builder()
                .name("Hollow Knight")
                .price(BigDecimal.valueOf(14.99))
                .stock(100)
                .isHighlight(true)
                .build();

        Game created = Game.builder().id("hollow-knight").name("Hollow Knight").price(BigDecimal.valueOf(14.99)).build();
        GameDTO dto = GameDTO.builder().id("hollow-knight").name("Hollow Knight").price(BigDecimal.valueOf(14.99)).build();

        when(gameService.createGame(any(CreateGameRequest.class))).thenReturn(created);
        when(labMapper.toGameDTO(created)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/admin/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("hollow-knight"))
                .andExpect(jsonPath("$.name").value("Hollow Knight"));
    }

    @Test
    void createGame_BlankName_ReturnsBadRequest() throws Exception {
        CreateGameRequest request = CreateGameRequest.builder()
                .name("")
                .price(BigDecimal.valueOf(14.99))
                .build();

        mockMvc.perform(post("/api/v1/admin/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void createGame_NegativePrice_ReturnsBadRequest() throws Exception {
        CreateGameRequest request = CreateGameRequest.builder()
                .name("Game")
                .price(BigDecimal.valueOf(-1.0))
                .build();

        mockMvc.perform(post("/api/v1/admin/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void updateGame_Valid_ReturnsOk() throws Exception {
        UpdateGameRequest request = UpdateGameRequest.builder()
                .name("Hades II")
                .price(BigDecimal.valueOf(29.99))
                .stock(50)
                .build();

        Game updated = Game.builder().id("hades").name("Hades II").price(BigDecimal.valueOf(29.99)).build();
        GameDTO dto = GameDTO.builder().id("hades").name("Hades II").price(BigDecimal.valueOf(29.99)).build();

        when(gameService.updateGame(eq("hades"), any(UpdateGameRequest.class))).thenReturn(updated);
        when(labMapper.toGameDTO(updated)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/admin/games/hades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hades II"));
    }

    @Test
    void updateGame_NotFound_ReturnsNotFound() throws Exception {
        UpdateGameRequest request = UpdateGameRequest.builder()
                .name("Hades II")
                .price(BigDecimal.valueOf(29.99))
                .build();

        when(gameService.updateGame(eq("non-existent"), any(UpdateGameRequest.class)))
                .thenThrow(new NoSuchElementException("Game not found with id: non-existent"));

        mockMvc.perform(put("/api/v1/admin/games/non-existent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Game not found with id: non-existent"));
    }

    @Test
    void updateHighlight_WithBody_ReturnsOk() throws Exception {
        GameHighlightRequest request = GameHighlightRequest.builder().isHighlight(true).build();
        Game toggled = Game.builder().id("celeste").isHighlight(true).build();
        GameDTO dto = GameDTO.builder().id("celeste").isHighlight(true).build();

        when(gameService.updateHighlight("celeste", true)).thenReturn(toggled);
        when(labMapper.toGameDTO(toggled)).thenReturn(dto);

        mockMvc.perform(patch("/api/v1/admin/games/celeste/highlight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isHighlight").value(true));
    }

    @Test
    void updateHighlight_WithoutBody_ReturnsOk() throws Exception {
        Game toggled = Game.builder().id("celeste").isHighlight(false).build();
        GameDTO dto = GameDTO.builder().id("celeste").isHighlight(false).build();

        when(gameService.updateHighlight("celeste", null)).thenReturn(toggled);
        when(labMapper.toGameDTO(toggled)).thenReturn(dto);

        mockMvc.perform(patch("/api/v1/admin/games/celeste/highlight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isHighlight").value(false));
    }

    @Test
    void toggleHighlightPost_ReturnsOk() throws Exception {
        Game toggled = Game.builder().id("celeste").isHighlight(true).build();
        GameDTO dto = GameDTO.builder().id("celeste").isHighlight(true).build();

        when(gameService.updateHighlight("celeste", null)).thenReturn(toggled);
        when(labMapper.toGameDTO(toggled)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/admin/games/celeste/toggle-highlight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isHighlight").value(true));
    }

    @Test
    void deleteGame_Success_ReturnsNoContent() throws Exception {
        doNothing().when(gameService).deleteGame("dead-cells");

        mockMvc.perform(delete("/api/v1/admin/games/dead-cells"))
                .andExpect(status().isNoContent());

        verify(gameService, times(1)).deleteGame("dead-cells");
    }

    @Test
    void deleteGame_NotFound_ReturnsNotFound() throws Exception {
        doThrow(new NoSuchElementException("Game not found with id: unknown"))
                .when(gameService).deleteGame("unknown");

        mockMvc.perform(delete("/api/v1/admin/games/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Game not found with id: unknown"));
    }
}
