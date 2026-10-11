package com.mousejerry.backend.repository;

import com.mousejerry.backend.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String>, JpaSpecificationExecutor<Game> {

    List<Game> findByIsHighlightTrue();
}
