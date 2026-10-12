package com.mousejerry.backend.dao;

import com.mousejerry.backend.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    List<Category> findAllVisible();

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    Optional<Category> findBySlug(String slug);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsBySlugAndIdNot(String slug, Long id);

    Category save(Category category);

    void delete(Category category);

    void deleteById(Long id);
}
