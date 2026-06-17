package com.egaming.clan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "clans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String clanName;

    private String clanTag;

    @Column(nullable = false)
    private Long ownerId;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String logo;

    private String bannerImage;

    private Integer memberCount = 1;

    private Integer maxMembers = 50;

    private Integer wins = 0;

    private Integer losses = 0;

    private Integer clanRating = 0;

    private Boolean isPublic = true;

    private Boolean requiresApproval = false;

    @Enumerated(EnumType.STRING)
    private ClanStatus status; // ACTIVE, INACTIVE, DISBANDED

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum ClanStatus {
        ACTIVE, INACTIVE, DISBANDED
    }
}
