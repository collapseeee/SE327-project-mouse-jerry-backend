package com.mousejerry.backend.dao.impl;

import com.mousejerry.backend.dao.CategoryDao;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("db")
@RequiredArgsConstructor
public class CategoryDaoDbImpl implements CategoryDao {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllVisible() {
        return categoryRepository.findByIsVisibleTrueOrderByNameAsc();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return categoryRepository.findBySlugIgnoreCase(slug);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return categoryRepository.existsBySlugIgnoreCase(slug);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return categoryRepository.existsByNameIgnoreCaseAndIdNot(name, id);
    }

    @Override
    public boolean existsBySlugAndIdNot(String slug, Long id) {
        return categoryRepository.existsBySlugIgnoreCaseAndIdNot(slug, id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
