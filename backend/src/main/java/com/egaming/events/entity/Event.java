package com.egaming.events.entity;

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
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long organizerId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String bannerImage;

    @Column(nullable = false)
    private String gameName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType; // TOURNAMENT, PRACTICE, FRIENDLY

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventFormat eventFormat; // SOLO, DUO, SQUAD, TEAM

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status; // DRAFT, SCHEDULED, ONGOING, COMPLETED, CANCELLED

    private Integer maxParticipants;

    private Integer currentParticipants = 0;

    private BigDecimal entryFee;

    private BigDecimal prizePool;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime registrationDeadline;

    private Integer totalMatches = 0;

    private Integer completedMatches = 0;

    @ElementCollection
    @CollectionTable(name = "event_rules", joinColumns = @JoinColumn(name = "event_id"))
    private Set<String> rules;

    @Column(nullable = false)
    private Boolean requiresVerification = false;

    private Boolean isFeatured = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum EventType {
        TOURNAMENT, PRACTICE, FRIENDLY, LEAGUE
    }

    public enum EventFormat {
        SOLO, DUO, SQUAD, TEAM
    }

    public enum EventStatus {
        DRAFT, SCHEDULED, ONGOING, COMPLETED, CANCELLED
    }
}
