package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.CategoryDTO;
import com.mousejerry.backend.dto.CategoryVisibilityRequest;
import com.mousejerry.backend.dto.CreateCategoryRequest;
import com.mousejerry.backend.dto.UpdateCategoryRequest;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.service.CategoryService;
import com.mousejerry.backend.util.LabMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final LabMapper labMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(labMapper.toCategoryDTOList(categories));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        Category created = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(labMapper.toCategoryDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request
    ) {
        Category updated = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(labMapper.toCategoryDTO(updated));
    }

    @PatchMapping("/{id}/visibility")
    public ResponseEntity<CategoryDTO> updateVisibility(
            @PathVariable Long id,
            @RequestBody(required = false) CategoryVisibilityRequest request
    ) {
        Boolean isVisible = request != null ? request.getIsVisible() : null;
        Category toggled = categoryService.toggleVisibility(id, isVisible);
        return ResponseEntity.ok(labMapper.toCategoryDTO(toggled));
    }

    @PostMapping("/{id}/toggle-visibility")
    public ResponseEntity<CategoryDTO> toggleVisibility(
            @PathVariable Long id,
            @RequestBody(required = false) CategoryVisibilityRequest request
    ) {
        return updateVisibility(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
