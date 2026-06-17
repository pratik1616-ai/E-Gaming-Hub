package com.egaming.rankings.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rankings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String gameName;

    @Column(nullable = false)
    private String country;

    private String region;

    @Column(nullable = false)
    private Integer globalRank;

    @Column(nullable = false)
    private Integer regionalRank;

    @Column(nullable = false)
    private Double rating = 0.0;

    private Integer totalMatches = 0;

    private Integer totalWins = 0;

    private Integer totalKills = 0;

    private Integer totalDeaths = 0;

    private Double winRate = 0.0;

    private Double kdr = 0.0;

    private Integer points = 0;

    private Integer streakWins = 0;

    private Integer streakLosses = 0;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}
