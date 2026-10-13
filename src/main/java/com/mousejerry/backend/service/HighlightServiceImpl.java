package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.dao.HighlightDao;
import com.mousejerry.backend.dto.CreateHighlightRequest;
import com.mousejerry.backend.dto.UpdateHighlightRequest;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.Highlight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HighlightServiceImpl implements HighlightService {

    private static final String DEFAULT_BUTTON_COLOR = "#8b0000";

    private final HighlightDao highlightDao;
    private final GameDao gameDao;

    @Override
    public List<Highlight> getAllHighlights() {
        return highlightDao.findAll();
    }

    @Override
    public Highlight getHighlightById(Long id) {
        return highlightDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Highlight not found with id: " + id));
    }

    @Override
    @Transactional
    public Highlight createHighlight(CreateHighlightRequest request) {
        Game game = gameDao.findById(request.getGameId())
                .orElseThrow(() -> new NoSuchElementException("Game not found with id: " + request.getGameId()));

        String color = sanitizeColor(request.getButtonColor());
        String image = sanitizeImage(request.getCustomImage());

        Highlight highlight = Highlight.builder()
                .game(game)
                .customImage(image)
                .buttonColor(color)
                .build();

        game.setIsHighlight(true);
        gameDao.save(game);

        return highlightDao.save(highlight);
    }

    @Override
    @Transactional
    public Highlight updateHighlight(Long id, UpdateHighlightRequest request) {
        Highlight highlight = getHighlightById(id);

        if (request.getGameId() != null && !request.getGameId().isBlank()
                && (highlight.getGame() == null || !request.getGameId().equals(highlight.getGame().getId()))) {
            Game newGame = gameDao.findById(request.getGameId())
                    .orElseThrow(() -> new NoSuchElementException("Game not found with id: " + request.getGameId()));
            highlight.setGame(newGame);
            newGame.setIsHighlight(true);
            gameDao.save(newGame);
        }

        if (request.getButtonColor() != null && !request.getButtonColor().isBlank()) {
            highlight.setButtonColor(sanitizeColor(request.getButtonColor()));
        }

        if (request.getCustomImage() != null) {
            highlight.setCustomImage(sanitizeImage(request.getCustomImage()));
        }

        return highlightDao.save(highlight);
    }

    @Override
    @Transactional
    public void deleteHighlight(Long id) {
        Highlight highlight = getHighlightById(id);
        highlightDao.delete(highlight);
    }

    private String sanitizeColor(String color) {
        if (color == null || color.isBlank()) {
            return DEFAULT_BUTTON_COLOR;
        }
        return color.trim();
    }

    private String sanitizeImage(String image) {
        if (image == null || image.isBlank()) {
            return null;
        }
        return image.trim();
    }
}
