package com.mousejerry.backend.dao.impl;

import com.mousejerry.backend.dao.HighlightDao;
import com.mousejerry.backend.entity.Highlight;
import com.mousejerry.backend.repository.HighlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class HighlightDaoDbImpl implements HighlightDao {

    private final HighlightRepository highlightRepository;

    @Override
    public List<Highlight> findAll() {
        return highlightRepository.findAllWithGame();
    }

    @Override
    public Optional<Highlight> findById(Long id) {
        return highlightRepository.findById(id);
    }

    @Override
    public Optional<Highlight> findByGameId(String gameId) {
        return highlightRepository.findByGameId(gameId);
    }

    @Override
    public Highlight save(Highlight highlight) {
        return highlightRepository.save(highlight);
    }

    @Override
    public void delete(Highlight highlight) {
        highlightRepository.delete(highlight);
    }
}
