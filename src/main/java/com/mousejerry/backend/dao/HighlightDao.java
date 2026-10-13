package com.mousejerry.backend.dao;

import com.mousejerry.backend.entity.Highlight;

import java.util.List;
import java.util.Optional;

public interface HighlightDao {

    List<Highlight> findAll();

    Optional<Highlight> findById(Long id);

    Optional<Highlight> findByGameId(String gameId);

    Highlight save(Highlight highlight);

    void delete(Highlight highlight);
}
