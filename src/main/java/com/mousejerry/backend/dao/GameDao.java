package com.mousejerry.backend.dao;

import com.mousejerry.backend.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GameDao {

    Page<Game> findGames(String category, String search, Pageable pageable);

    Optional<Game> findById(String id);

    List<Game> findHighlights();

    Game save(Game game);

    void delete(Game game);

    void deleteById(String id);

    boolean existsById(String id);
}
