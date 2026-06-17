package com.egaming.admin.controller;

import com.egaming.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin panel endpoints")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard stats")
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(adminService.getAllUsers(page, size));
    }

    @PostMapping("/users/{userId}/ban")
    @Operation(summary = "Ban user")
    public ResponseEntity<?> banUser(@PathVariable Long userId, @RequestParam String reason) {
        return ResponseEntity.ok(adminService.banUser(userId, reason));
    }

    @PostMapping("/users/{userId}/unban")
    @Operation(summary = "Unban user")
    public ResponseEntity<?> unbanUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.unbanUser(userId));
    }

    @GetMapping("/events")
    @Operation(summary = "Get all events")
    public ResponseEntity<?> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(adminService.getAllEvents(page, size));
    }

    @PostMapping("/events/{eventId}/approve")
    @Operation(summary = "Approve event")
    public ResponseEntity<?> approveEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(adminService.approveEvent(eventId));
    }

    @PostMapping("/events/{eventId}/reject")
    @Operation(summary = "Reject event")
    public ResponseEntity<?> rejectEvent(@PathVariable Long eventId, @RequestParam String reason) {
        return ResponseEntity.ok(adminService.rejectEvent(eventId, reason));
    }

    @GetMapping("/reports")
    @Operation(summary = "Get user reports")
    public ResponseEntity<?> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(adminService.getReports(page, size));
    }

    @PostMapping("/reports/{reportId}/resolve")
    @Operation(summary = "Resolve report")
    public ResponseEntity<?> resolveReport(@PathVariable Long reportId, @RequestParam String resolution) {
        return ResponseEntity.ok(adminService.resolveReport(reportId, resolution));
    }

    @GetMapping("/transactions")
    @Operation(summary = "Get all transactions")
    public ResponseEntity<?> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(adminService.getAllTransactions(page, size));
    }
}
