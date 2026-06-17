package com.egaming.community.controller;

import com.egaming.community.dto.CommentCreateRequest;
import com.egaming.community.service.CommentService;
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
@RequestMapping("/community/comments")
@RequiredArgsConstructor
@Tag(name = "Community Comments", description = "Comment management endpoints")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create comment")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(request));
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Get post comments")
    public ResponseEntity<?> getPostComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(commentService.getPostComments(postId, pageable));
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("@securityService.isCommentOwner(#commentId)")
    @Operation(summary = "Update comment")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentCreateRequest request) {
        return ResponseEntity.ok(commentService.updateComment(commentId, request));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("@securityService.isCommentOwner(#commentId)")
    @Operation(summary = "Delete comment")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted");
    }
}
