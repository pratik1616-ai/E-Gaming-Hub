package com.egaming.fantasy.controller;

import com.egaming.fantasy.dto.FantasyContestCreateRequest;
import com.egaming.fantasy.dto.FantasyTeamCreateRequest;
import com.egaming.fantasy.service.FantasyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fantasy")
@RequiredArgsConstructor
@Tag(name = "Fantasy Gaming", description = "Fantasy contest endpoints")
public class FantasyController {
    private final FantasyService fantasyService;

    @PostMapping("/contests")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create fantasy contest")
    public ResponseEntity<?> createContest(@Valid @RequestBody FantasyContestCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fantasyService.createContest(request));
    }

    @GetMapping("/contests/{contestId}")
    @Operation(summary = "Get contest details")
    public ResponseEntity<?> getContest(@PathVariable Long contestId) {
        return ResponseEntity.ok(fantasyService.getContest(contestId));
    }

    @GetMapping("/contests")
    @Operation(summary = "Get all contests")
    public ResponseEntity<?> getAllContests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(fantasyService.getAllContests(pageable));
    }

    @PostMapping("/teams")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create fantasy team")
    public ResponseEntity<?> createFantasyTeam(@Valid @RequestBody FantasyTeamCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fantasyService.createFantasyTeam(request));
    }

    @GetMapping("/contests/{contestId}/leaderboard")
    @Operation(summary = "Get contest leaderboard")
    public ResponseEntity<?> getContestLeaderboard(@PathVariable Long contestId) {
        return ResponseEntity.ok(fantasyService.getContestLeaderboard(contestId));
    }

    @GetMapping("/contests/{contestId}/join")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Join fantasy contest")
    public ResponseEntity<?> joinContest(@PathVariable Long contestId) {
        return ResponseEntity.ok(fantasyService.joinContest(contestId));
    }

    @GetMapping("/user/{userId}/contests")
    @Operation(summary = "Get user contests")
    public ResponseEntity<?> getUserContests(@PathVariable Long userId) {
        return ResponseEntity.ok(fantasyService.getUserContests(userId));
    }
}
