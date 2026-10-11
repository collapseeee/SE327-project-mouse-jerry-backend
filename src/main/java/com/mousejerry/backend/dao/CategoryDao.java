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

    Category save(Category category);
}
