package com.mousejerry.backend.repository;

import com.mousejerry.backend.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String>, JpaSpecificationExecutor<Game> {

    @Override
    @EntityGraph(attributePaths = {"primaryCategory", "categories", "screenshots"})
    Optional<Game> findById(String id);

    @EntityGraph(attributePaths = {"primaryCategory", "categories", "screenshots"})
    List<Game> findByIsHighlightTrue();

    @Override
    @EntityGraph(attributePaths = {"primaryCategory", "categories", "screenshots"})
    Page<Game> findAll(@Nullable Specification<Game> spec, Pageable pageable);
}

