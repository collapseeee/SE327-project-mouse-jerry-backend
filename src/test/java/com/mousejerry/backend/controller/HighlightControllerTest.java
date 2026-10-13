package com.mousejerry.backend.controller;

import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.HighlightDTO;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HighlightControllerTest {

    @Mock
    private HighlightService highlightService;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private HighlightController highlightController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(highlightController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getHighlights_ReturnsList() throws Exception {
        Game game = Game.builder().id("elden-ring").name("Elden Ring").build();
        Highlight highlight = Highlight.builder().id(1L).game(game).buttonColor("#8b0000").build();
        HighlightDTO dto = HighlightDTO.builder().id(1L).gameId("elden-ring").gameName("Elden Ring").buttonColor("#8b0000").build();

        when(highlightService.getAllHighlights()).thenReturn(List.of(highlight));
        when(labMapper.toHighlightDTOList(any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/highlights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameId").value("elden-ring"))
                .andExpect(jsonPath("$[0].buttonColor").value("#8b0000"));

        verify(highlightService).getAllHighlights();
    }
}
