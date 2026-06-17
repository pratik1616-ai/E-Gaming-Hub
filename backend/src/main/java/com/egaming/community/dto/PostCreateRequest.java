package com.egaming.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateRequest {
    @NotBlank(message = "Content is required")
    private String content;

    private String imageUrl;
}
