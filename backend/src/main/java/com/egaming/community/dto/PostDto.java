package com.egaming.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long userId;
    private String content;
    private String imageUrl;
    private Long likeCount;
    private Long commentCount;
    private Long shareCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
