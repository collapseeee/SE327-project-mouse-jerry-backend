package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.CreateHighlightRequest;
import com.mousejerry.backend.dto.HighlightDTO;
import com.mousejerry.backend.dto.UpdateHighlightRequest;
import com.mousejerry.backend.entity.Highlight;
import com.mousejerry.backend.service.HighlightService;
import com.mousejerry.backend.util.LabMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/highlights")
@RequiredArgsConstructor
public class AdminHighlightController {

    private final HighlightService highlightService;
    private final LabMapper labMapper;

    @GetMapping
    public ResponseEntity<List<HighlightDTO>> getAllHighlights() {
        List<Highlight> highlights = highlightService.getAllHighlights();
        return ResponseEntity.ok(labMapper.toHighlightDTOList(highlights));
    }

    @PostMapping
    public ResponseEntity<HighlightDTO> createHighlight(@Valid @RequestBody CreateHighlightRequest request) {
        Highlight created = highlightService.createHighlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(labMapper.toHighlightDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HighlightDTO> updateHighlight(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHighlightRequest request
    ) {
        Highlight updated = highlightService.updateHighlight(id, request);
        return ResponseEntity.ok(labMapper.toHighlightDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHighlight(@PathVariable Long id) {
        highlightService.deleteHighlight(id);
        return ResponseEntity.noContent().build();
    }
}
