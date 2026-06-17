package com.egaming.events.controller;

import com.egaming.events.dto.EventCreateRequest;
import com.egaming.events.dto.EventDto;
import com.egaming.events.service.EventService;
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
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Gaming Events", description = "Event management endpoints")
public class EventController {
    private final EventService eventService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    @Operation(summary = "Create new event")
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(request));
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "Get event details")
    public ResponseEntity<?> getEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @GetMapping
    @Operation(summary = "Get all events")
    public ResponseEntity<?> getAllEvents(
            @RequestParam(required = false) String gameName,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.getAllEvents(gameName, status, pageable));
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("@securityService.isEventOrganizer(#eventId)")
    @Operation(summary = "Update event")
    public ResponseEntity<?> updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventCreateRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, request));
    }

    @PostMapping("/{eventId}/register")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Register for event")
    public ResponseEntity<?> registerForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.registerForEvent(eventId));
    }

    @DeleteMapping("/{eventId}/register")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cancel event registration")
    public ResponseEntity<?> cancelRegistration(@PathVariable Long eventId) {
        eventService.cancelRegistration(eventId);
        return ResponseEntity.ok("Registration cancelled");
    }

    @GetMapping("/{eventId}/registrations")
    @PreAuthorize("@securityService.isEventOrganizer(#eventId)")
    @Operation(summary = "Get event registrations")
    public ResponseEntity<?> getRegistrations(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getRegistrations(eventId));
    }

    @GetMapping("/{eventId}/leaderboard")
    @Operation(summary = "Get event leaderboard")
    public ResponseEntity<?> getLeaderboard(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getLeaderboard(eventId));
    }

    @PostMapping("/{eventId}/start")
    @PreAuthorize("@securityService.isEventOrganizer(#eventId)")
    @Operation(summary = "Start event")
    public ResponseEntity<?> startEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.startEvent(eventId));
    }

    @PostMapping("/{eventId}/end")
    @PreAuthorize("@securityService.isEventOrganizer(#eventId)")
    @Operation(summary = "End event")
    public ResponseEntity<?> endEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.endEvent(eventId));
    }
}
