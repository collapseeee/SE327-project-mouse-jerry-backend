package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CategoryDao;
import com.mousejerry.backend.dto.CreateCategoryRequest;
import com.mousejerry.backend.dto.UpdateCategoryRequest;
import com.mousejerry.backend.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public List<Category> getVisibleCategories() {
        return categoryDao.findAllVisible();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
    }

    @Override
    public Category getCategoryByNameOrSlug(String identifier) {
        return categoryDao.findBySlug(identifier)
                .or(() -> categoryDao.findByName(identifier))
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + identifier));
    }

    @Override
    @Transactional
    public Category createCategory(CreateCategoryRequest request) {
        String name = request.getName() != null ? request.getName().trim() : "";
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be blank");
        }
        if (categoryDao.existsByName(name)) {
            throw new IllegalArgumentException("Category already exists with name: " + name);
        }

        String slug = resolveSlug(name, request.getSlug());
        if (categoryDao.existsBySlug(slug)) {
            throw new IllegalArgumentException("Category already exists with slug: " + slug);
        }

        Category category = Category.builder()
                .name(name)
                .slug(slug)
                .isVisible(request.getIsVisible() != null ? request.getIsVisible() : true)
                .build();

        return categoryDao.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = getCategoryById(id);
        String newName = request.getName() != null ? request.getName().trim() : "";
        if (newName.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be blank");
        }
        if (categoryDao.existsByNameAndIdNot(newName, id)) {
            throw new IllegalArgumentException("Category already exists with name: " + newName);
        }

        String newSlug;
        if (request.getSlug() != null && !request.getSlug().trim().isEmpty()) {
            newSlug = request.getSlug().trim();
        } else {
            newSlug = resolveSlug(newName, null);
        }

        if (categoryDao.existsBySlugAndIdNot(newSlug, id)) {
            throw new IllegalArgumentException("Category already exists with slug: " + newSlug);
        }

        category.setName(newName);
        category.setSlug(newSlug);
        if (request.getIsVisible() != null) {
            category.setIsVisible(request.getIsVisible());
        }

        return categoryDao.save(category);
    }

    @Override
    @Transactional
    public Category toggleVisibility(Long id, Boolean isVisible) {
        Category category = getCategoryById(id);
        if (isVisible != null) {
            category.setIsVisible(isVisible);
        } else {
            category.setIsVisible(!Boolean.TRUE.equals(category.getIsVisible()));
        }
        return categoryDao.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        if (category.getGames() != null) {
            category.getGames().forEach(game -> {
                if (game.getCategories() != null) {
                    game.getCategories().remove(category);
                }
                if (category.equals(game.getPrimaryCategory())) {
                    game.setPrimaryCategory(null);
                }
            });
        }
        categoryDao.delete(category);
    }

    private String resolveSlug(String name, String customSlug) {
        if (customSlug != null && !customSlug.trim().isEmpty()) {
            return customSlug.trim();
        }
        String slug = name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
        return slug.isEmpty() ? name : slug;
    }
}
