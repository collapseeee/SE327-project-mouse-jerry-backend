package com.mousejerry.backend.repository;

import com.mousejerry.backend.entity.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighlightRepository extends JpaRepository<Highlight, Long> {

    @Query("SELECT h FROM Highlight h JOIN FETCH h.game ORDER BY h.id ASC")
    List<Highlight> findAllWithGame();

    Optional<Highlight> findByGameId(String gameId);
}
