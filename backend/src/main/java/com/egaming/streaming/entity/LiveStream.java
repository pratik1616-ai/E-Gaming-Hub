package com.egaming.streaming.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "live_streams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveStream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long streamerId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnail;

    @Column(nullable = false)
    private String gameName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StreamStatus status; // SCHEDULED, LIVE, ENDED

    @Column(nullable = false)
    private String streamUrl;

    private String rtmpUrl;

    private String streamKey;

    private Long viewerCount = 0L;

    private Long likeCount = 0L;

    private Long commentCount = 0L;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long totalViewerMinutes = 0L;

    private Double avgConcurrentViewers = 0.0;

    private Boolean allowChat = true;

    private Boolean allowGifts = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum StreamStatus {
        SCHEDULED, LIVE, ENDED, CANCELLED
    }
}
