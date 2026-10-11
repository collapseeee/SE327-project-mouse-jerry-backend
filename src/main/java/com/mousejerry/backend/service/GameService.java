package com.mousejerry.backend.service;

import com.mousejerry.backend.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {

    Page<Game> getGames(String category, String search, Pageable pageable);

    Game getGameById(String id);

    List<Game> getHighlightedGames();
}
