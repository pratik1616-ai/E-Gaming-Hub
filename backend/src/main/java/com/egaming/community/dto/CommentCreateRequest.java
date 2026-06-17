package com.egaming.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateRequest {
    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotBlank(message = "Comment text is required")
    private String content;
}
