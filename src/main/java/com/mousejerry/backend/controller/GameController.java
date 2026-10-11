package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.GameDTO;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.service.GameService;
import com.mousejerry.backend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/games", "/api/games"})
@RequiredArgsConstructor
public class GameController {

    public static final int MAX_PAGE_SIZE = 100;

    private final GameService gameService;
    private final LabMapper labMapper;

    @GetMapping
    public ResponseEntity<List<GameDTO>> getGames(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        int pageNumber = Math.max(page - 1, 0);
        int pageSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Sort.Direction sortDir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDir, sort));

        Page<Game> gamePage = gameService.getGames(category, search, pageable);
        List<GameDTO> dtos = labMapper.toGameDTOList(gamePage.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(gamePage.getTotalElements()));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "X-Total-Count");

        return ResponseEntity.ok()
                .headers(headers)
                .body(dtos);
    }

    @GetMapping("/highlights")
    public ResponseEntity<List<GameDTO>> getHighlights() {
        List<Game> highlights = gameService.getHighlightedGames();
        return ResponseEntity.ok(labMapper.toGameDTOList(highlights));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable String id) {
        Game game = gameService.getGameById(id);
        return ResponseEntity.ok(labMapper.toGameDTO(game));
    }
}
