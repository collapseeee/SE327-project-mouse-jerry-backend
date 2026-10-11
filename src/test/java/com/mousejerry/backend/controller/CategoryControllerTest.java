package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.CategoryDTO;
import com.mousejerry.backend.entity.Category;
import com.mousejerry.backend.service.CategoryService;
import com.mousejerry.backend.util.LabMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new com.mousejerry.backend.config.GlobalExceptionHandler())
                .build();
    }

    @Test
    void getCategories_ReturnsListOfCategories() throws Exception {
        Category cat = Category.builder().id(1L).name("Fighting").slug("Fighting").isVisible(true).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("Fighting").slug("Fighting").isVisible(true).build();

        when(categoryService.getVisibleCategories()).thenReturn(List.of(cat));
        when(labMapper.toCategoryDTOList(List.of(cat))).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Fighting"));
    }

    @Test
    void getCategoryById_Found_ReturnsCategory() throws Exception {
        Category cat = Category.builder().id(1L).name("Fighting").slug("Fighting").isVisible(true).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("Fighting").slug("Fighting").isVisible(true).build();

        when(categoryService.getCategoryById(1L)).thenReturn(cat);
        when(labMapper.toCategoryDTO(cat)).thenReturn(dto);

        mockMvc.perform(get("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fighting"));
    }

    @Test
    void getCategoryById_NotFound_Returns404() throws Exception {
        when(categoryService.getCategoryById(999L)).thenThrow(new NoSuchElementException("Category not found with id: 999"));

        mockMvc.perform(get("/api/categories/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Category not found with id: 999"));
    }
}
