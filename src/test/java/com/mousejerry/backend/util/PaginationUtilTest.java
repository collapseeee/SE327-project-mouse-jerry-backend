package com.mousejerry.backend.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginationUtilTest {

    @Test
    void testCreatePageableAscending() {
        Pageable pageable = PaginationUtil.createPageable(2, 20, "name", "asc", 100);
        assertEquals(1, pageable.getPageNumber());
        assertEquals(20, pageable.getPageSize());
        assertEquals(Sort.Direction.ASC, pageable.getSort().getOrderFor("name").getDirection());
    }

    @Test
    void testCreatePageableDescendingAndBounds() {
        Pageable pageable = PaginationUtil.createPageable(0, 500, "price", "desc", 50);
        assertEquals(0, pageable.getPageNumber());
        assertEquals(50, pageable.getPageSize());
        assertEquals(Sort.Direction.DESC, pageable.getSort().getOrderFor("price").getDirection());
    }

    @Test
    void testToPagedResponseHeaders() {
        Page<String> page = new PageImpl<>(List.of("gameA", "gameB"), Pageable.unpaged(), 42);
        ResponseEntity<List<String>> response = PaginationUtil.toPagedResponse(page, List.of("gameA", "gameB"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("42", response.getHeaders().getFirst("X-Total-Count"));
        assertEquals("X-Total-Count", response.getHeaders().getFirst("Access-Control-Expose-Headers"));
        assertEquals(List.of("gameA", "gameB"), response.getBody());
    }
}
