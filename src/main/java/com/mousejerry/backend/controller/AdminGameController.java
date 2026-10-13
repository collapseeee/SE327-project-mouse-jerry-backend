package com.mousejerry.backend.controller;

import com.mousejerry.backend.dto.CreateGameRequest;
import com.mousejerry.backend.dto.GameDTO;
import com.mousejerry.backend.dto.GameHighlightRequest;
import com.mousejerry.backend.dto.UpdateGameRequest;
import com.mousejerry.backend.entity.Game;
import com.mousejerry.backend.service.GameService;
import com.mousejerry.backend.util.LabMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/games")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminGameController {

    public static final int MAX_PAGE_SIZE = 100;

    private final GameService gameService;
    private final LabMapper labMapper;

    @GetMapping
    public ResponseEntity<List<GameDTO>> getAllGames(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = com.mousejerry.backend.util.PaginationUtil.createPageable(page, size, sort, direction, MAX_PAGE_SIZE);
        Page<Game> gamePage = gameService.getGames(category, search, pageable);
        List<GameDTO> dtos = labMapper.toGameDTOList(gamePage.getContent());
        return com.mousejerry.backend.util.PaginationUtil.toPagedResponse(gamePage, dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable String id) {
        Game game = gameService.getGameById(id);
        return ResponseEntity.ok(labMapper.toGameDTO(game));
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@Valid @RequestBody CreateGameRequest request) {
        Game created = gameService.createGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(labMapper.toGameDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(
            @PathVariable String id,
            @Valid @RequestBody UpdateGameRequest request
    ) {
        Game updated = gameService.updateGame(id, request);
        return ResponseEntity.ok(labMapper.toGameDTO(updated));
    }

    @PatchMapping("/{id}/highlight")
    public ResponseEntity<GameDTO> updateHighlight(
            @PathVariable String id,
            @RequestBody(required = false) GameHighlightRequest request
    ) {
        Boolean isHighlight = request != null ? request.getIsHighlight() : null;
        Game toggled = gameService.updateHighlight(id, isHighlight);
        return ResponseEntity.ok(labMapper.toGameDTO(toggled));
    }

    @PostMapping("/{id}/toggle-highlight")
    public ResponseEntity<GameDTO> toggleHighlight(
            @PathVariable String id,
            @RequestBody(required = false) GameHighlightRequest request
    ) {
        return updateHighlight(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
