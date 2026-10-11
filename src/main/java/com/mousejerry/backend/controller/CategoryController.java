package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.CategoryDTO;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.service.CategoryService;
import com.mousejerry.backend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/categories", "/api/categories"})
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final LabMapper labMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<Category> categories = categoryService.getVisibleCategories();
        return ResponseEntity.ok(labMapper.toCategoryDTOList(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(labMapper.toCategoryDTO(category));
    }
}
