package com.egaming.clan.controller;

import com.egaming.clan.dto.ClanCreateRequest;
import com.egaming.clan.service.ClanService;
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
@RequestMapping("/clans")
@RequiredArgsConstructor
@Tag(name = "Clan Management", description = "Clan management endpoints")
public class ClanController {
    private final ClanService clanService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create clan")
    public ResponseEntity<?> createClan(@Valid @RequestBody ClanCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clanService.createClan(request));
    }

    @GetMapping("/{clanId}")
    @Operation(summary = "Get clan details")
    public ResponseEntity<?> getClan(@PathVariable Long clanId) {
        return ResponseEntity.ok(clanService.getClan(clanId));
    }

    @GetMapping
    @Operation(summary = "Get all clans")
    public ResponseEntity<?> getAllClans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(clanService.getAllClans(pageable));
    }

    @PostMapping("/{clanId}/invite/{userId}")
    @PreAuthorize("@securityService.isClanAdmin(#clanId)")
    @Operation(summary = "Invite user to clan")
    public ResponseEntity<?> inviteUser(@PathVariable Long clanId, @PathVariable Long userId) {
        return ResponseEntity.ok(clanService.inviteUser(clanId, userId));
    }

    @PostMapping("/{clanId}/join")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Request to join clan")
    public ResponseEntity<?> joinClan(@PathVariable Long clanId) {
        return ResponseEntity.ok(clanService.requestJoin(clanId));
    }

    @PostMapping("/{clanId}/approve/{userId}")
    @PreAuthorize("@securityService.isClanAdmin(#clanId)")
    @Operation(summary = "Approve join request")
    public ResponseEntity<?> approveMember(@PathVariable Long clanId, @PathVariable Long userId) {
        return ResponseEntity.ok(clanService.approveMember(clanId, userId));
    }

    @GetMapping("/{clanId}/members")
    @Operation(summary = "Get clan members")
    public ResponseEntity<?> getMembers(@PathVariable Long clanId) {
        return ResponseEntity.ok(clanService.getMembers(clanId));
    }
}
