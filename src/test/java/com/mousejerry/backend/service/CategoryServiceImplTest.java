package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.CategoryDao;
import com.mousejerry.backend.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getVisibleCategories_ReturnsList() {
        Category cat = Category.builder().id(1L).name("Fighting").isVisible(true).build();
        when(categoryDao.findAllVisible()).thenReturn(List.of(cat));

        List<Category> result = categoryService.getVisibleCategories();
        assertEquals(1, result.size());
        assertEquals("Fighting", result.get(0).getName());
    }

    @Test
    void getAllCategories_ReturnsList() {
        Category cat = Category.builder().id(1L).name("Fighting").isVisible(false).build();
        when(categoryDao.findAll()).thenReturn(List.of(cat));

        List<Category> result = categoryService.getAllCategories();
        assertEquals(1, result.size());
    }

    @Test
    void getCategoryById_Found_ReturnsCategory() {
        Category cat = Category.builder().id(1L).name("Fighting").build();
        when(categoryDao.findById(1L)).thenReturn(Optional.of(cat));

        Category result = categoryService.getCategoryById(1L);
        assertNotNull(result);
        assertEquals("Fighting", result.getName());
    }

    @Test
    void getCategoryById_NotFound_ThrowsException() {
        when(categoryDao.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> categoryService.getCategoryById(99L));
    }

    @Test
    void getCategoryByNameOrSlug_FoundBySlug() {
        Category cat = Category.builder().id(1L).slug("fighting").name("Fighting").build();
        when(categoryDao.findBySlug("fighting")).thenReturn(Optional.of(cat));

        Category result = categoryService.getCategoryByNameOrSlug("fighting");
        assertNotNull(result);
        assertEquals("Fighting", result.getName());
    }

    @Test
    void getCategoryByNameOrSlug_FoundByName() {
        Category cat = Category.builder().id(1L).slug("fighting").name("Fighting").build();
        when(categoryDao.findBySlug("Fighting")).thenReturn(Optional.empty());
        when(categoryDao.findByName("Fighting")).thenReturn(Optional.of(cat));

        Category result = categoryService.getCategoryByNameOrSlug("Fighting");
        assertNotNull(result);
        assertEquals("Fighting", result.getName());
    }

    @Test
    void getCategoryByNameOrSlug_NotFound_ThrowsException() {
        when(categoryDao.findBySlug("unknown")).thenReturn(Optional.empty());
        when(categoryDao.findByName("unknown")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> categoryService.getCategoryByNameOrSlug("unknown"));
    }
}
