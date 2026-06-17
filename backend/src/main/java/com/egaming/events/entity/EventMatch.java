package com.egaming.events.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private Integer roundNumber;

    @Column(nullable = false)
    private Integer matchNumber;

    private Long team1Id;

    private Long team2Id;

    private Long player1Id;

    private Long player2Id;

    @Enumerated(EnumType.STRING)
    private MatchStatus status; // SCHEDULED, ONGOING, COMPLETED, CANCELLED

    private LocalDateTime scheduledTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String mapName;

    private Long winnerId;

    private Integer team1Score = 0;

    private Integer team2Score = 0;

    @Column(columnDefinition = "TEXT")
    private String matchStats;

    private String matchResultJson;

    private String liveStreamUrl;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum MatchStatus {
        SCHEDULED, ONGOING, COMPLETED, CANCELLED, POSTPONED
    }
}
