package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CategoryDao;
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
}
