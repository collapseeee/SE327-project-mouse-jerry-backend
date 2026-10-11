package com.mousejerry.backend.service;

import com.mousejerry.backend.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getVisibleCategories();

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByNameOrSlug(String identifier);
}
