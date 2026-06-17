package com.egaming.user.controller;

import com.egaming.user.dto.UserProfileDto;
import com.egaming.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User profile endpoints")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("@securityService.isOwner(#userId)")
    @Operation(summary = "Update user profile")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileDto dto) {
        return ResponseEntity.ok(userService.updateUserProfile(userId, dto));
    }

    @GetMapping("/{userId}/achievements")
    @Operation(summary = "Get user achievements")
    public ResponseEntity<?> getUserAchievements(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserAchievements(userId));
    }

    @GetMapping("/{userId}/stats")
    @Operation(summary = "Get user gaming stats")
    public ResponseEntity<?> getUserStats(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserStats(userId));
    }

    @PostMapping("/{userId}/avatar")
    @PreAuthorize("@securityService.isOwner(#userId)")
    @Operation(summary = "Upload avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long userId, @RequestParam String imageUrl) {
        return ResponseEntity.ok(userService.uploadAvatar(userId, imageUrl));
    }

    @GetMapping("/{userId}/followers")
    @Operation(summary = "Get user followers")
    public ResponseEntity<?> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    @Operation(summary = "Get user following")
    public ResponseEntity<?> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getFollowing(userId));
    }

    @PostMapping("/{userId}/follow/{targetUserId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Follow a user")
    public ResponseEntity<?> followUser(@PathVariable Long userId, @PathVariable Long targetUserId) {
        return ResponseEntity.ok(userService.followUser(userId, targetUserId));
    }

    @DeleteMapping("/{userId}/follow/{targetUserId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Unfollow a user")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId, @PathVariable Long targetUserId) {
        userService.unfollowUser(userId, targetUserId);
        return ResponseEntity.ok("Unfollowed successfully");
    }
}
