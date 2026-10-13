package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.dao.HighlightDao;
import com.mousejerry.backend.dto.CreateHighlightRequest;
import com.mousejerry.backend.dto.UpdateHighlightRequest;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.Highlight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HighlightServiceImplTest {

    @Mock
    private HighlightDao highlightDao;

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private HighlightServiceImpl highlightService;

    private Game sampleGame;
    private Highlight sampleHighlight;

    @BeforeEach
    void setUp() {
        sampleGame = Game.builder()
                .id("elden-ring")
                .name("Elden Ring")
                .isHighlight(false)
                .build();

        sampleHighlight = Highlight.builder()
                .id(1L)
                .game(sampleGame)
                .customImage("https://img.test/elden.jpg")
                .buttonColor("#8b0000")
                .build();
    }

    @Test
    void getAllHighlights_ReturnsList() {
        when(highlightDao.findAll()).thenReturn(List.of(sampleHighlight));

        List<Highlight> result = highlightService.getAllHighlights();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getButtonColor()).isEqualTo("#8b0000");
    }

    @Test
    void getHighlightById_Found_ReturnsHighlight() {
        when(highlightDao.findById(1L)).thenReturn(Optional.of(sampleHighlight));

        Highlight result = highlightService.getHighlightById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getHighlightById_NotFound_ThrowsNoSuchElementException() {
        when(highlightDao.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> highlightService.getHighlightById(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Highlight not found with id: 99");
    }

    @Test
    void createHighlight_ValidRequest_Success() {
        CreateHighlightRequest request = CreateHighlightRequest.builder()
                .gameId("elden-ring")
                .customImage(" https://img.test/custom.jpg ")
                .buttonColor(" #00c853 ")
                .build();

        when(gameDao.findById("elden-ring")).thenReturn(Optional.of(sampleGame));
        when(highlightDao.save(any(Highlight.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Highlight created = highlightService.createHighlight(request);

        assertThat(created).isNotNull();
        assertThat(created.getCustomImage()).isEqualTo("https://img.test/custom.jpg");
        assertThat(created.getButtonColor()).isEqualTo("#00c853");
        assertThat(sampleGame.getIsHighlight()).isTrue();
        verify(gameDao).save(sampleGame);
        verify(highlightDao).save(any(Highlight.class));
    }

    @Test
    void createHighlight_BlankColorAndImage_UsesDefaults() {
        CreateHighlightRequest request = CreateHighlightRequest.builder()
                .gameId("elden-ring")
                .customImage("   ")
                .buttonColor(null)
                .build();

        when(gameDao.findById("elden-ring")).thenReturn(Optional.of(sampleGame));
        when(highlightDao.save(any(Highlight.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Highlight created = highlightService.createHighlight(request);

        assertThat(created.getCustomImage()).isNull();
        assertThat(created.getButtonColor()).isEqualTo("#8b0000");
    }

    @Test
    void createHighlight_GameNotFound_ThrowsException() {
        CreateHighlightRequest request = CreateHighlightRequest.builder()
                .gameId("missing-game")
                .build();

        when(gameDao.findById("missing-game")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> highlightService.createHighlight(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Game not found with id: missing-game");
    }

    @Test
    void updateHighlight_UpdatesFields() {
        Game newGame = Game.builder().id("sekiro").name("Sekiro").isHighlight(false).build();
        UpdateHighlightRequest request = UpdateHighlightRequest.builder()
                .gameId("sekiro")
                .customImage("https://img.test/sekiro.jpg")
                .buttonColor("#DE1A58")
                .build();

        when(highlightDao.findById(1L)).thenReturn(Optional.of(sampleHighlight));
        when(gameDao.findById("sekiro")).thenReturn(Optional.of(newGame));
        when(highlightDao.save(any(Highlight.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Highlight updated = highlightService.updateHighlight(1L, request);

        assertThat(updated.getGame()).isEqualTo(newGame);
        assertThat(updated.getCustomImage()).isEqualTo("https://img.test/sekiro.jpg");
        assertThat(updated.getButtonColor()).isEqualTo("#DE1A58");
        assertThat(newGame.getIsHighlight()).isTrue();
    }

    @Test
    void updateHighlight_BlankImageClearsCustomImage() {
        UpdateHighlightRequest request = UpdateHighlightRequest.builder()
                .customImage("  ")
                .build();

        when(highlightDao.findById(1L)).thenReturn(Optional.of(sampleHighlight));
        when(highlightDao.save(any(Highlight.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Highlight updated = highlightService.updateHighlight(1L, request);

        assertThat(updated.getCustomImage()).isNull();
    }

    @Test
    void deleteHighlight_Success() {
        when(highlightDao.findById(1L)).thenReturn(Optional.of(sampleHighlight));

        highlightService.deleteHighlight(1L);

        verify(highlightDao).delete(sampleHighlight);
    }
}
