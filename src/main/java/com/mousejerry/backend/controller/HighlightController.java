package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.HighlightDTO;
import com.mousejerry.backend.entity.Highlight;
import com.mousejerry.backend.service.HighlightService;
import com.mousejerry.backend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/highlights")
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;
    private final LabMapper labMapper;

    @GetMapping
    public ResponseEntity<List<HighlightDTO>> getHighlights() {
        List<Highlight> highlights = highlightService.getAllHighlights();
        return ResponseEntity.ok(labMapper.toHighlightDTOList(highlights));
    }
}
