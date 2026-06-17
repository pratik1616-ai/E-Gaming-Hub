package com.egaming.fantasy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fantasy_teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FantasyTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id", nullable = false)
    private FantasyContest contest;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String teamName;

    @ElementCollection
    @CollectionTable(name = "fantasy_team_players", joinColumns = @JoinColumn(name = "team_id"))
    private Set<Long> playerIds; // 5 players

    private Long captainId;

    private Long viceCaptainId;

    private Double currentPoints = 0.0;

    private Integer currentRank = 0;

    private Boolean isConfirmed = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
