package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.CreateGameRequest;
import com.mousejerry.backend.dto.UpdateGameRequest;
import com.mousejerry.backend.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {

    Page<Game> getGames(String category, String search, Pageable pageable);

    Game getGameById(String id);

    List<Game> getHighlightedGames();

    Game createGame(CreateGameRequest request);

    Game updateGame(String id, UpdateGameRequest request);

    Game updateHighlight(String id, Boolean isHighlight);

    void deleteGame(String id);
}
