package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CategoryDao;
import com.mousejerry.backend.dao.GameDao;
import com.mousejerry.backend.dto.CreateGameRequest;
import com.mousejerry.backend.dto.SystemRequirementsDTO;
import com.mousejerry.backend.dto.UpdateGameRequest;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.entity.GameScreenshot;
import com.mousejerry.backend.entity.SystemRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;
    private final CategoryDao categoryDao;

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

    @Override
    @Transactional
    public Game createGame(CreateGameRequest request) {
        validateCreateRequest(request);
        String gameId = determineGameId(request.getId(), request.getName());
        Category primaryCategory = resolvePrimaryCategory(request.getPrimaryCategoryId(), request.getCategory());
        Set<Category> categories = resolveCategories(request.getCategoryIds(), request.getCategories(), primaryCategory);

        Game game = Game.builder()
                .id(gameId)
                .name(request.getName().trim())
                .price(request.getPrice())
                .coverImage(request.getCoverImage())
                .description(request.getDescription())
                .publisher(request.getPublisher())
                .releaseDate(request.getReleaseDate())
                .stock(request.getStock() != null ? request.getStock() : 0)
                .isHighlight(Boolean.TRUE.equals(request.getIsHighlight()))
                .primaryCategory(primaryCategory)
                .categories(categories)
                .screenshots(new ArrayList<>())
                .build();

        syncScreenshots(game, request.getScreenshots());
        syncSystemRequirements(game, request.getSystemRequirements());
        return gameDao.save(game);
    }

    @Override
    @Transactional
    public Game updateGame(String id, UpdateGameRequest request) {
        validateUpdateRequest(request);
        Game game = getGameById(id);

        game.setName(request.getName().trim());
        game.setPrice(request.getPrice());
        game.setDescription(request.getDescription());
        game.setCoverImage(request.getCoverImage());
        game.setPublisher(request.getPublisher());
        game.setReleaseDate(request.getReleaseDate());

        if (request.getStock() != null) {
            game.setStock(request.getStock());
        }
        if (request.getIsHighlight() != null) {
            game.setIsHighlight(request.getIsHighlight());
        }

        Category primaryCategory = resolvePrimaryCategory(request.getPrimaryCategoryId(), request.getCategory());
        game.setPrimaryCategory(primaryCategory);
        Set<Category> categories = resolveCategories(request.getCategoryIds(), request.getCategories(), primaryCategory);
        game.setCategories(categories);

        syncScreenshots(game, request.getScreenshots());
        syncSystemRequirements(game, request.getSystemRequirements());
        return gameDao.save(game);
    }

    @Override
    @Transactional
    public Game updateHighlight(String id, Boolean isHighlight) {
        Game game = getGameById(id);
        boolean target = isHighlight != null ? isHighlight : !Boolean.TRUE.equals(game.getIsHighlight());
        game.setIsHighlight(target);
        return gameDao.save(game);
    }

    @Override
    @Transactional
    public void deleteGame(String id) {
        Game game = getGameById(id);
        gameDao.delete(game);
    }

    private void validateCreateRequest(CreateGameRequest request) {
        if (request == null || request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Game name cannot be empty");
        }
        if (request.getPrice() == null || request.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    private void validateUpdateRequest(UpdateGameRequest request) {
        if (request == null || request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Game name cannot be empty");
        }
        if (request.getPrice() == null || request.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    private String determineGameId(String requestedId, String name) {
        if (requestedId != null && !requestedId.trim().isEmpty()) {
            String cleanId = requestedId.trim().toLowerCase(Locale.ROOT);
            if (gameDao.existsById(cleanId)) {
                throw new IllegalArgumentException("Game with id '" + cleanId + "' already exists");
            }
            return cleanId;
        }
        return generateUniqueSlug(name);
    }

    private String generateUniqueSlug(String name) {
        String baseSlug = toSlug(name);
        if (!gameDao.existsById(baseSlug)) {
            return baseSlug;
        }
        return baseSlug + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String toSlug(String input) {
        if (input == null || input.isBlank()) {
            return "game-" + UUID.randomUUID().toString().substring(0, 8);
        }
        String slug = input.trim().toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-+)|(-+$)", "");
        return slug.isEmpty() ? "game-" + UUID.randomUUID().toString().substring(0, 8) : slug;
    }

    private Category resolvePrimaryCategory(Long primaryCategoryId, String categoryName) {
        if (primaryCategoryId != null) {
            return categoryDao.findById(primaryCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Primary category not found with id: " + primaryCategoryId));
        }
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            return resolveCategoryByNameOrSlug(categoryName).orElse(null);
        }
        return null;
    }

    private Set<Category> resolveCategories(Set<Long> categoryIds, List<String> categoryNames, Category primaryCategory) {
        Set<Category> categories = new HashSet<>();
        if (primaryCategory != null) {
            categories.add(primaryCategory);
        }
        addCategoriesById(categories, categoryIds);
        addCategoriesByName(categories, categoryNames);
        return categories;
    }

    private void addCategoriesById(Set<Category> categories, Set<Long> categoryIds) {
        if (categoryIds == null) {
            return;
        }
        for (Long id : categoryIds) {
            categoryDao.findById(id).ifPresent(categories::add);
        }
    }

    private void addCategoriesByName(Set<Category> categories, List<String> categoryNames) {
        if (categoryNames == null) {
            return;
        }
        for (String name : categoryNames) {
            resolveCategoryByNameOrSlug(name).ifPresent(categories::add);
        }
    }

    private Optional<Category> resolveCategoryByNameOrSlug(String nameOrSlug) {
        if (nameOrSlug == null || nameOrSlug.isBlank()) {
            return Optional.empty();
        }
        String clean = nameOrSlug.trim();
        return categoryDao.findByName(clean).or(() -> categoryDao.findBySlug(clean));
    }

    private void syncScreenshots(Game game, List<String> screenshotUrls) {
        game.getScreenshots().clear();
        if (screenshotUrls == null) {
            return;
        }
        for (int i = 0; i < screenshotUrls.size(); i++) {
            String url = screenshotUrls.get(i);
            if (url != null && !url.isBlank()) {
                GameScreenshot screenshot = GameScreenshot.builder()
                        .game(game)
                        .url(url.trim())
                        .displayOrder(i)
                        .build();
                game.getScreenshots().add(screenshot);
            }
        }
    }

    private void syncSystemRequirements(Game game, SystemRequirementsDTO dto) {
        if (dto == null) {
            game.setSystemRequirements(null);
            return;
        }
        SystemRequirements req = game.getSystemRequirements();
        if (req == null) {
            req = SystemRequirements.builder()
                    .game(game)
                    .build();
            game.setSystemRequirements(req);
        }
        req.setOs(dto.getOs());
        req.setProcessor(dto.getProcessor());
        req.setMemory(dto.getMemory());
        req.setGraphics(dto.getGraphics());
        req.setStorage(dto.getStorage());
    }
}
