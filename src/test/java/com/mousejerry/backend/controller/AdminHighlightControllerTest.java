package com.mousejerry.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.CreateHighlightRequest;
import com.mousejerry.backend.dto.HighlightDTO;
import com.mousejerry.backend.dto.UpdateHighlightRequest;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.Highlight;
import com.mousejerry.backend.service.HighlightService;
import com.mousejerry.backend.util.LabMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminHighlightControllerTest {

    @Mock
    private HighlightService highlightService;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private AdminHighlightController adminHighlightController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Highlight sampleHighlight;
    private HighlightDTO sampleHighlightDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminHighlightController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();

        Game game = Game.builder().id("elden-ring").name("Elden Ring").build();
        sampleHighlight = Highlight.builder()
                .id(1L)
                .game(game)
                .customImage("https://img.test/banner.jpg")
                .buttonColor("#8b0000")
                .build();

        sampleHighlightDTO = HighlightDTO.builder()
                .id(1L)
                .gameId("elden-ring")
                .gameName("Elden Ring")
                .customImage("https://img.test/banner.jpg")
                .buttonColor("#8b0000")
                .build();
    }

    @Test
    void getAllHighlights_ReturnsList() throws Exception {
        when(highlightService.getAllHighlights()).thenReturn(List.of(sampleHighlight));
        when(labMapper.toHighlightDTOList(any())).thenReturn(List.of(sampleHighlightDTO));

        mockMvc.perform(get("/api/v1/admin/highlights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].gameName").value("Elden Ring"))
                .andExpect(jsonPath("$[0].buttonColor").value("#8b0000"));

        verify(highlightService).getAllHighlights();
    }

    @Test
    void createHighlight_ValidRequest_ReturnsCreated() throws Exception {
        CreateHighlightRequest request = CreateHighlightRequest.builder()
                .gameId("elden-ring")
                .customImage("https://img.test/banner.jpg")
                .buttonColor("#8b0000")
                .build();

        when(highlightService.createHighlight(any(CreateHighlightRequest.class))).thenReturn(sampleHighlight);
        when(labMapper.toHighlightDTO(sampleHighlight)).thenReturn(sampleHighlightDTO);

        mockMvc.perform(post("/api/v1/admin/highlights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.gameId").value("elden-ring"));

        verify(highlightService).createHighlight(any(CreateHighlightRequest.class));
    }

    @Test
    void createHighlight_BlankGameId_ReturnsBadRequest() throws Exception {
        CreateHighlightRequest request = CreateHighlightRequest.builder()
                .gameId("")
                .buttonColor("#8b0000")
                .build();

        mockMvc.perform(post("/api/v1/admin/highlights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateHighlight_ValidRequest_ReturnsUpdated() throws Exception {
        UpdateHighlightRequest request = UpdateHighlightRequest.builder()
                .buttonColor("#00c853")
                .customImage("https://img.test/new-banner.jpg")
                .build();

        HighlightDTO updatedDTO = HighlightDTO.builder()
                .id(1L)
                .gameId("elden-ring")
                .gameName("Elden Ring")
                .customImage("https://img.test/new-banner.jpg")
                .buttonColor("#00c853")
                .build();

        when(highlightService.updateHighlight(eq(1L), any(UpdateHighlightRequest.class))).thenReturn(sampleHighlight);
        when(labMapper.toHighlightDTO(sampleHighlight)).thenReturn(updatedDTO);

        mockMvc.perform(put("/api/v1/admin/highlights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buttonColor").value("#00c853"));

        verify(highlightService).updateHighlight(eq(1L), any(UpdateHighlightRequest.class));
    }

    @Test
    void updateHighlight_NotFound_Returns404() throws Exception {
        UpdateHighlightRequest request = UpdateHighlightRequest.builder()
                .buttonColor("#00c853")
                .build();

        when(highlightService.updateHighlight(eq(99L), any(UpdateHighlightRequest.class)))
                .thenThrow(new NoSuchElementException("Highlight not found with id: 99"));

        mockMvc.perform(put("/api/v1/admin/highlights/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteHighlight_Success_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/highlights/1"))
                .andExpect(status().isNoContent());

        verify(highlightService).deleteHighlight(1L);
    }
}
