package com.mousejerry.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.CategoryDTO;
import com.mousejerry.backend.dto.CategoryVisibilityRequest;
import com.mousejerry.backend.dto.CreateCategoryRequest;
import com.mousejerry.backend.dto.UpdateCategoryRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminCategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private LabMapper labMapper;

    @InjectMocks
    private AdminCategoryController adminCategoryController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminCategoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllCategories_ReturnsList() throws Exception {
        Category cat = Category.builder().id(1L).name("RPG").slug("rpg").isVisible(true).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("RPG").slug("rpg").isVisible(true).build();

        when(categoryService.getAllCategories()).thenReturn(List.of(cat));
        when(labMapper.toCategoryDTOList(List.of(cat))).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("RPG"));
    }

    @Test
    void createCategory_Valid_ReturnsCreated() throws Exception {
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("Simulation")
                .slug("simulation")
                .isVisible(true)
                .build();

        Category created = Category.builder().id(10L).name("Simulation").slug("simulation").isVisible(true).build();
        CategoryDTO dto = CategoryDTO.builder().id(10L).name("Simulation").slug("simulation").isVisible(true).build();

        when(categoryService.createCategory(any(CreateCategoryRequest.class))).thenReturn(created);
        when(labMapper.toCategoryDTO(created)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Simulation"));
    }

    @Test
    void createCategory_BlankName_ReturnsBadRequest() throws Exception {
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("")
                .build();

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void updateCategory_Valid_ReturnsOk() throws Exception {
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .name("Action RPG")
                .slug("action-rpg")
                .isVisible(true)
                .build();

        Category updated = Category.builder().id(1L).name("Action RPG").slug("action-rpg").isVisible(true).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("Action RPG").slug("action-rpg").isVisible(true).build();

        when(categoryService.updateCategory(eq(1L), any(UpdateCategoryRequest.class))).thenReturn(updated);
        when(labMapper.toCategoryDTO(updated)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/admin/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Action RPG"));
    }

    @Test
    void updateCategory_NotFound_ReturnsNotFound() throws Exception {
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .name("Action RPG")
                .build();

        when(categoryService.updateCategory(eq(99L), any(UpdateCategoryRequest.class)))
                .thenThrow(new NoSuchElementException("Category not found with id: 99"));

        mockMvc.perform(put("/api/v1/admin/categories/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Category not found with id: 99"));
    }

    @Test
    void updateCategory_DuplicateName_ReturnsBadRequest() throws Exception {
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .name("Fighting")
                .build();

        when(categoryService.updateCategory(eq(1L), any(UpdateCategoryRequest.class)))
                .thenThrow(new IllegalArgumentException("Category already exists with name: Fighting"));

        mockMvc.perform(put("/api/v1/admin/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Category already exists with name: Fighting"));
    }

    @Test
    void updateVisibility_WithBody_ReturnsOk() throws Exception {
        CategoryVisibilityRequest request = CategoryVisibilityRequest.builder().isVisible(false).build();
        Category toggled = Category.builder().id(1L).name("Fighting").isVisible(false).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("Fighting").isVisible(false).build();

        when(categoryService.toggleVisibility(1L, false)).thenReturn(toggled);
        when(labMapper.toCategoryDTO(toggled)).thenReturn(dto);

        mockMvc.perform(patch("/api/v1/admin/categories/1/visibility")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isVisible").value(false));
    }

    @Test
    void updateVisibility_WithoutBody_ReturnsOk() throws Exception {
        Category toggled = Category.builder().id(1L).name("Fighting").isVisible(false).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("Fighting").isVisible(false).build();

        when(categoryService.toggleVisibility(1L, null)).thenReturn(toggled);
        when(labMapper.toCategoryDTO(toggled)).thenReturn(dto);

        mockMvc.perform(patch("/api/v1/admin/categories/1/visibility"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isVisible").value(false));
    }

    @Test
    void toggleVisibilityPost_ReturnsOk() throws Exception {
        Category toggled = Category.builder().id(1L).name("Fighting").isVisible(false).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("Fighting").isVisible(false).build();

        when(categoryService.toggleVisibility(1L, null)).thenReturn(toggled);
        when(labMapper.toCategoryDTO(toggled)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/admin/categories/1/toggle-visibility"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isVisible").value(false));
    }

    @Test
    void deleteCategory_Success_ReturnsNoContent() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/v1/admin/categories/1"))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategory(1L);
    }

    @Test
    void deleteCategory_NotFound_ReturnsNotFound() throws Exception {
        doThrow(new NoSuchElementException("Category not found with id: 99"))
                .when(categoryService).deleteCategory(99L);

        mockMvc.perform(delete("/api/v1/admin/categories/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Category not found with id: 99"));
    }

    @Test
    void toggleVisibilityPost_WithBody_ReturnsOk() throws Exception {
        CategoryVisibilityRequest request = CategoryVisibilityRequest.builder().isVisible(true).build();
        Category toggled = Category.builder().id(1L).name("Fighting").isVisible(true).build();
        CategoryDTO dto = CategoryDTO.builder().id(1L).name("Fighting").isVisible(true).build();

        when(categoryService.toggleVisibility(1L, true)).thenReturn(toggled);
        when(labMapper.toCategoryDTO(toggled)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/admin/categories/1/toggle-visibility")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isVisible").value(true));
    }

    @Test
    void createCategory_DuplicateName_ReturnsBadRequest() throws Exception {
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("Fighting")
                .build();

        when(categoryService.createCategory(any(CreateCategoryRequest.class)))
                .thenThrow(new IllegalArgumentException("Category already exists with name: Fighting"));

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Category already exists with name: Fighting"));
    }

    @Test
    void createCategory_DuplicateSlug_ReturnsBadRequest() throws Exception {
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("Fighting 2")
                .slug("fighting")
                .build();

        when(categoryService.createCategory(any(CreateCategoryRequest.class)))
                .thenThrow(new IllegalArgumentException("Category already exists with slug: fighting"));

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Category already exists with slug: fighting"));
    }

    @Test
    void updateCategory_DuplicateSlug_ReturnsBadRequest() throws Exception {
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .name("RPG")
                .slug("fighting")
                .build();

        when(categoryService.updateCategory(eq(1L), any(UpdateCategoryRequest.class)))
                .thenThrow(new IllegalArgumentException("Category already exists with slug: fighting"));

        mockMvc.perform(put("/api/v1/admin/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Category already exists with slug: fighting"));
    }

    @Test
    void createCategory_NameTooLong_ReturnsBadRequest() throws Exception {
        String longName = "A".repeat(101);
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name(longName)
                .build();

        mockMvc.perform(post("/api/v1/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void updateCategory_NameTooLong_ReturnsBadRequest() throws Exception {
        String longName = "A".repeat(101);
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .name(longName)
                .build();

        mockMvc.perform(put("/api/v1/admin/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }
}
