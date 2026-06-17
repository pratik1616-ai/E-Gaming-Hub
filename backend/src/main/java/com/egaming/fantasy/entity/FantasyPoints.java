package com.egaming.fantasy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fantasy_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FantasyPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private FantasyTeam fantasyTeam;

    @Column(nullable = false)
    private Long playerId;

    private Double pointsEarned = 0.0;

    private Integer kills = 0;

    private Integer deaths = 0;

    private Integer assists = 0;

    private Boolean participated = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime calculatedAt;
}
