package com.egaming.streaming.controller;

import com.egaming.streaming.dto.StreamCreateRequest;
import com.egaming.streaming.service.StreamingService;
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
@RequestMapping("/streams")
@RequiredArgsConstructor
@Tag(name = "Live Streaming", description = "Live streaming endpoints")
public class StreamingController {
    private final StreamingService streamingService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Start live stream")
    public ResponseEntity<?> startStream(@Valid @RequestBody StreamCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(streamingService.startStream(request));
    }

    @GetMapping("/{streamId}")
    @Operation(summary = "Get stream details")
    public ResponseEntity<?> getStream(@PathVariable Long streamId) {
        return ResponseEntity.ok(streamingService.getStream(streamId));
    }

    @GetMapping("/live")
    @Operation(summary = "Get all live streams")
    public ResponseEntity<?> getLiveStreams(
            @RequestParam(required = false) String gameName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(streamingService.getLiveStreams(gameName, pageable));
    }

    @PostMapping("/{streamId}/end")
    @PreAuthorize("@securityService.isStreamer(#streamId)")
    @Operation(summary = "End live stream")
    public ResponseEntity<?> endStream(@PathVariable Long streamId) {
        return ResponseEntity.ok(streamingService.endStream(streamId));
    }

    @PostMapping("/{streamId}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Like stream")
    public ResponseEntity<?> likeStream(@PathVariable Long streamId) {
        return ResponseEntity.ok(streamingService.likeStream(streamId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user streams")
    public ResponseEntity<?> getUserStreams(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(streamingService.getUserStreams(userId, pageable));
    }
}
