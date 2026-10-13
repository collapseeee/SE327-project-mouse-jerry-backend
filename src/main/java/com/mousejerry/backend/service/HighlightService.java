package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.CreateHighlightRequest;
import com.mousejerry.backend.dto.UpdateHighlightRequest;
import com.mousejerry.backend.entity.Highlight;

import java.util.List;

public interface HighlightService {

    List<Highlight> getAllHighlights();

    Highlight getHighlightById(Long id);

    Highlight createHighlight(CreateHighlightRequest request);

    Highlight updateHighlight(Long id, UpdateHighlightRequest request);

    void deleteHighlight(Long id);
}
