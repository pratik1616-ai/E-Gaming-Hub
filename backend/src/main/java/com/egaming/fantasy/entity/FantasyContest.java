package com.egaming.fantasy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fantasy_contests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FantasyContest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long creatorId;

    @Column(nullable = false)
    private String contestName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String matchType; // BGMI, FREE_FIRE, VALORANT, etc.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestStatus status; // ACTIVE, CLOSED, LIVE, COMPLETED

    @Column(nullable = false)
    private BigDecimal entryFee;

    @Column(nullable = false)
    private BigDecimal prizePool;

    private Integer maxParticipants;

    private Integer currentParticipants = 0;

    private Integer payoutPercentage = 50;

    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum ContestStatus {
        ACTIVE, CLOSED, LIVE, COMPLETED, CANCELLED
    }
}
