package com.egaming.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType; // IMAGE, VIDEO

    @Column(columnDefinition = "TEXT")
    private String caption;

    private Long viewCount = 0L;

    private Long reactionCount = 0L;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum MediaType {
        IMAGE, VIDEO
    }
}
