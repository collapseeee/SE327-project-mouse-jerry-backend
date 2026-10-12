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

    @Test
    void createCategory_Success() {
        com.mousejerry.backend.dto.CreateCategoryRequest req = com.mousejerry.backend.dto.CreateCategoryRequest.builder()
                .name("Survival Horror")
                .isVisible(true)
                .build();

        when(categoryDao.existsByName("Survival Horror")).thenReturn(false);
        when(categoryDao.existsBySlug("survival-horror")).thenReturn(false);
        when(categoryDao.save(org.mockito.ArgumentMatchers.any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = categoryService.createCategory(req);
        assertNotNull(result);
        assertEquals("Survival Horror", result.getName());
        assertEquals("survival-horror", result.getSlug());
        assertTrue(result.getIsVisible());
    }

    @Test
    void createCategory_CustomSlug_Success() {
        com.mousejerry.backend.dto.CreateCategoryRequest req = com.mousejerry.backend.dto.CreateCategoryRequest.builder()
                .name("Survival Horror")
                .slug("custom-horror")
                .build();

        when(categoryDao.existsByName("Survival Horror")).thenReturn(false);
        when(categoryDao.existsBySlug("custom-horror")).thenReturn(false);
        when(categoryDao.save(org.mockito.ArgumentMatchers.any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = categoryService.createCategory(req);
        assertEquals("custom-horror", result.getSlug());
        assertTrue(result.getIsVisible());
    }

    @Test
    void createCategory_BlankName_ThrowsIllegalArgument() {
        com.mousejerry.backend.dto.CreateCategoryRequest req = com.mousejerry.backend.dto.CreateCategoryRequest.builder()
                .name("   ")
                .build();

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(req));
    }

    @Test
    void createCategory_DuplicateName_ThrowsIllegalArgument() {
        com.mousejerry.backend.dto.CreateCategoryRequest req = com.mousejerry.backend.dto.CreateCategoryRequest.builder()
                .name("Fighting")
                .build();

        when(categoryDao.existsByName("Fighting")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(req));
    }

    @Test
    void createCategory_DuplicateSlug_ThrowsIllegalArgument() {
        com.mousejerry.backend.dto.CreateCategoryRequest req = com.mousejerry.backend.dto.CreateCategoryRequest.builder()
                .name("Fighting 2")
                .slug("fighting")
                .build();

        when(categoryDao.existsByName("Fighting 2")).thenReturn(false);
        when(categoryDao.existsBySlug("fighting")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(req));
    }

    @Test
    void updateCategory_Success() {
        Category existing = Category.builder().id(1L).name("Fighting").slug("fighting").isVisible(true).build();
        com.mousejerry.backend.dto.UpdateCategoryRequest req = com.mousejerry.backend.dto.UpdateCategoryRequest.builder()
                .name("Brawler")
                .isVisible(false)
                .build();

        when(categoryDao.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryDao.existsByNameAndIdNot("Brawler", 1L)).thenReturn(false);
        when(categoryDao.existsBySlugAndIdNot("brawler", 1L)).thenReturn(false);
        when(categoryDao.save(org.mockito.ArgumentMatchers.any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = categoryService.updateCategory(1L, req);
        assertEquals("Brawler", result.getName());
        assertEquals("brawler", result.getSlug());
        assertFalse(result.getIsVisible());
    }

    @Test
    void updateCategory_CustomSlug_Success() {
        Category existing = Category.builder().id(1L).name("Fighting").slug("fighting").isVisible(true).build();
        com.mousejerry.backend.dto.UpdateCategoryRequest req = com.mousejerry.backend.dto.UpdateCategoryRequest.builder()
                .name("Brawler")
                .slug("custom-brawler")
                .build();

        when(categoryDao.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryDao.existsByNameAndIdNot("Brawler", 1L)).thenReturn(false);
        when(categoryDao.existsBySlugAndIdNot("custom-brawler", 1L)).thenReturn(false);
        when(categoryDao.save(org.mockito.ArgumentMatchers.any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = categoryService.updateCategory(1L, req);
        assertEquals("custom-brawler", result.getSlug());
    }

    @Test
    void updateCategory_BlankName_ThrowsIllegalArgument() {
        Category existing = Category.builder().id(1L).name("Fighting").build();
        when(categoryDao.findById(1L)).thenReturn(Optional.of(existing));

        com.mousejerry.backend.dto.UpdateCategoryRequest req = com.mousejerry.backend.dto.UpdateCategoryRequest.builder()
                .name("  ")
                .build();

        assertThrows(IllegalArgumentException.class, () -> categoryService.updateCategory(1L, req));
    }

    @Test
    void updateCategory_DuplicateName_ThrowsIllegalArgument() {
        Category existing = Category.builder().id(1L).name("Fighting").build();
        when(categoryDao.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryDao.existsByNameAndIdNot("RPG", 1L)).thenReturn(true);

        com.mousejerry.backend.dto.UpdateCategoryRequest req = com.mousejerry.backend.dto.UpdateCategoryRequest.builder()
                .name("RPG")
                .build();

        assertThrows(IllegalArgumentException.class, () -> categoryService.updateCategory(1L, req));
    }

    @Test
    void updateCategory_DuplicateSlug_ThrowsIllegalArgument() {
        Category existing = Category.builder().id(1L).name("Fighting").build();
        when(categoryDao.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryDao.existsByNameAndIdNot("RPG", 1L)).thenReturn(false);
        when(categoryDao.existsBySlugAndIdNot("rpg", 1L)).thenReturn(true);

        com.mousejerry.backend.dto.UpdateCategoryRequest req = com.mousejerry.backend.dto.UpdateCategoryRequest.builder()
                .name("RPG")
                .build();

        assertThrows(IllegalArgumentException.class, () -> categoryService.updateCategory(1L, req));
    }

    @Test
    void toggleVisibility_WithExplicitBoolean() {
        Category existing = Category.builder().id(1L).name("Fighting").isVisible(true).build();
        when(categoryDao.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryDao.save(org.mockito.ArgumentMatchers.any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = categoryService.toggleVisibility(1L, false);
        assertFalse(result.getIsVisible());
    }

    @Test
    void toggleVisibility_WithoutExplicitBoolean_InvertsCurrent() {
        Category existing = Category.builder().id(1L).name("Fighting").isVisible(true).build();
        when(categoryDao.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryDao.save(org.mockito.ArgumentMatchers.any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = categoryService.toggleVisibility(1L, null);
        assertFalse(result.getIsVisible());

        // Toggle again
        Category result2 = categoryService.toggleVisibility(1L, null);
        assertTrue(result2.getIsVisible());
    }

    @Test
    void deleteCategory_Success_CleansUpRelationships() {
        Category cat = Category.builder().id(1L).name("Fighting").build();
        com.mousejerry.backend.entity.Game game = com.mousejerry.backend.entity.Game.builder()
                .id("g1")
                .primaryCategory(cat)
                .categories(new java.util.HashSet<>(java.util.List.of(cat)))
                .build();
        cat.setGames(new java.util.HashSet<>(java.util.List.of(game)));

        when(categoryDao.findById(1L)).thenReturn(Optional.of(cat));

        categoryService.deleteCategory(1L);

        assertNull(game.getPrimaryCategory());
        assertFalse(game.getCategories().contains(cat));
        org.mockito.Mockito.verify(categoryDao).delete(cat);
    }
}
