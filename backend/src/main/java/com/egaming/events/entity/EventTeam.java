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
@Table(name = "event_teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private Long captainId;

    private String logo;

    @ElementCollection
    @CollectionTable(name = "event_team_members", joinColumns = @JoinColumn(name = "team_id"))
    private Set<Long> memberIds;

    private Integer wins = 0;

    private Integer losses = 0;

    private Integer draws = 0;

    private Double points = 0.0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
