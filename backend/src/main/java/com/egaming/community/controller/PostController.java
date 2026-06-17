package com.egaming.community.controller;

import com.egaming.community.dto.PostCreateRequest;
import com.egaming.community.dto.PostDto;
import com.egaming.community.service.PostService;
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
@RequestMapping("/community/posts")
@RequiredArgsConstructor
@Tag(name = "Community Posts", description = "Post management endpoints")
public class PostController {
    private final PostService postService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create new post")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get post details")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping
    @Operation(summary = "Get all posts (paginated)")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user posts")
    public ResponseEntity<?> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getUserPosts(userId, pageable));
    }

    @PutMapping("/{postId}")
    @PreAuthorize("@securityService.isPostOwner(#postId)")
    @Operation(summary = "Update post")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @Valid @RequestBody PostCreateRequest request) {
        return ResponseEntity.ok(postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("@securityService.isPostOwner(#postId)")
    @Operation(summary = "Delete post")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PostMapping("/{postId}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Like a post")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.likePost(postId));
    }

    @DeleteMapping("/{postId}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Unlike a post")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId) {
        postService.unlikePost(postId);
        return ResponseEntity.ok("Post unliked");
    }
}
