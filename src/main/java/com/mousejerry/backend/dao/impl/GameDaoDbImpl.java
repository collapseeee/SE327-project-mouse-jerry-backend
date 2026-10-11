package com.mousejerry.backend.dao.impl;

import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.repository.GameRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class GameDaoDbImpl implements GameDao {

    private final GameRepository gameRepository;

    @Override
    public Page<Game> findGames(String category, String search, Pageable pageable) {
        Specification<Game> spec = buildSpecification(category, search);
        return gameRepository.findAll(spec, pageable);
    }

    public Specification<Game> buildSpecification(String category, String search) {
        return (root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            List<Predicate> predicates = new ArrayList<>();

            if (category != null && !category.trim().isEmpty()) {
                String catLower = category.trim().toLowerCase();
                Join<Game, Category> primaryCatJoin = root.join("primaryCategory", JoinType.LEFT);
                Join<Game, Category> catJoin = root.join("categories", JoinType.LEFT);

                Predicate primaryCatMatch = cb.equal(cb.lower(primaryCatJoin.get("name")), catLower);
                Predicate primaryCatSlugMatch = cb.equal(cb.lower(primaryCatJoin.get("slug")), catLower);
                Predicate catMatch = cb.equal(cb.lower(catJoin.get("name")), catLower);
                Predicate catSlugMatch = cb.equal(cb.lower(catJoin.get("slug")), catLower);

                predicates.add(cb.or(primaryCatMatch, primaryCatSlugMatch, catMatch, catSlugMatch));
            }

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim().toLowerCase() + "%";
                Predicate nameMatch = cb.like(cb.lower(root.get("name")), searchPattern);

                Join<Game, Category> searchCatJoin = root.join("categories", JoinType.LEFT);
                Predicate catNameMatch = cb.like(cb.lower(searchCatJoin.get("name")), searchPattern);

                predicates.add(cb.or(nameMatch, catNameMatch));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Optional<Game> findById(String id) {
        return gameRepository.findById(id);
    }

    @Override
    public List<Game> findHighlights() {
        return gameRepository.findByIsHighlightTrue();
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }
}
