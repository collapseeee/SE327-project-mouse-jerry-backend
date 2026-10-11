package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;

    @Override
    public Page<Game> getGames(String category, String search, Pageable pageable) {
        return gameDao.findGames(category, search, pageable);
    }

    @Override
    public Game getGameById(String id) {
        return gameDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Game not found with id: " + id));
    }

    @Override
    public List<Game> getHighlightedGames() {
        return gameDao.findHighlights();
    }
}
