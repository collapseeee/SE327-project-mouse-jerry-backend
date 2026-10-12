package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.CreateCategoryRequest;
import com.mousejerry.backend.dto.UpdateCategoryRequest;
import com.mousejerry.backend.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getVisibleCategories();

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByNameOrSlug(String identifier);

    Category createCategory(CreateCategoryRequest request);

    Category updateCategory(Long id, UpdateCategoryRequest request);

    Category toggleVisibility(Long id, Boolean isVisible);

    void deleteCategory(Long id);
}
