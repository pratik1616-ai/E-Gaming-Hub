package com.egaming.rankings.controller;

import com.egaming.rankings.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankings")
@RequiredArgsConstructor
@Tag(name = "Rankings", description = "Global ranking endpoints")
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/global")
    @Operation(summary = "Get global rankings")
    public ResponseEntity<?> getGlobalRankings(
            @RequestParam String gameName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(rankingService.getGlobalRankings(gameName, pageable));
    }

    @GetMapping("/regional")
    @Operation(summary = "Get regional rankings")
    public ResponseEntity<?> getRegionalRankings(
            @RequestParam String gameName,
            @RequestParam String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(rankingService.getRegionalRankings(gameName, country, pageable));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user ranking")
    public ResponseEntity<?> getUserRanking(@PathVariable Long userId) {
        return ResponseEntity.ok(rankingService.getUserRanking(userId));
    }

    @GetMapping("/user/{userId}/{gameName}")
    @Operation(summary = "Get user ranking for specific game")
    public ResponseEntity<?> getUserGameRanking(@PathVariable Long userId, @PathVariable String gameName) {
        return ResponseEntity.ok(rankingService.getUserGameRanking(userId, gameName));
    }
}
