package com.mousejerry.backend.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class PaginationUtil {

    public static final int DEFAULT_MAX_PAGE_SIZE = 100;

    private PaginationUtil() {
    }

    public static Pageable createPageable(int page, int size, String sort, String direction, int maxPageSize) {
        int pageNumber = Math.max(page - 1, 0);
        int pageSize = Math.clamp(size, 1, maxPageSize);
        Sort.Direction sortDir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(pageNumber, pageSize, Sort.by(sortDir, sort));
    }

    public static <T> ResponseEntity<List<T>> toPagedResponse(Page<?> page, List<T> content) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "X-Total-Count");
        return ResponseEntity.ok().headers(headers).body(content);
    }
}
