package com.mousejerry.backend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDtoTest {

    @Test
    void testCreateCategoryRequest() {
        CreateCategoryRequest req1 = new CreateCategoryRequest();
        req1.setName("RPG");
        req1.setSlug("rpg");
        req1.setIsVisible(true);

        CreateCategoryRequest req2 = new CreateCategoryRequest("RPG", "rpg", true);
        CreateCategoryRequest req3 = CreateCategoryRequest.builder()
                .name("RPG")
                .slug("rpg")
                .isVisible(true)
                .build();

        assertEquals(req1, req2);
        assertEquals(req2, req3);
        assertEquals(req1.hashCode(), req2.hashCode());
        assertEquals("RPG", req1.getName());
        assertEquals("rpg", req1.getSlug());
        assertTrue(req1.getIsVisible());
        assertTrue(req1.toString().contains("RPG"));
    }

    @Test
    void testUpdateCategoryRequest() {
        UpdateCategoryRequest req1 = new UpdateCategoryRequest();
        req1.setName("Action");
        req1.setSlug("action");
        req1.setIsVisible(false);

        UpdateCategoryRequest req2 = new UpdateCategoryRequest("Action", "action", false);
        UpdateCategoryRequest req3 = UpdateCategoryRequest.builder()
                .name("Action")
                .slug("action")
                .isVisible(false)
                .build();

        assertEquals(req1, req2);
        assertEquals(req2, req3);
        assertEquals(req1.hashCode(), req2.hashCode());
        assertEquals("Action", req1.getName());
        assertEquals("action", req1.getSlug());
        assertFalse(req1.getIsVisible());
        assertTrue(req1.toString().contains("Action"));
    }

    @Test
    void testCategoryVisibilityRequest() {
        CategoryVisibilityRequest req1 = new CategoryVisibilityRequest();
        req1.setIsVisible(true);

        CategoryVisibilityRequest req2 = new CategoryVisibilityRequest(true);
        CategoryVisibilityRequest req3 = CategoryVisibilityRequest.builder()
                .isVisible(true)
                .build();

        assertEquals(req1, req2);
        assertEquals(req2, req3);
        assertEquals(req1.hashCode(), req2.hashCode());
        assertTrue(req1.getIsVisible());
        assertTrue(req1.toString().contains("true"));
    }
}
