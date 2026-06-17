package com.egaming.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long userId;

    private String gamingId;

    private Integer rank = 0;

    private Integer totalMatches = 0;

    private Integer totalWins = 0;

    private Double winRate = 0.0;

    private Integer totalKills = 0;

    private Integer totalDeaths = 0;

    private Double kdr = 0.0;

    private Integer rating = 0;

    private String preferredGame;

    @ElementCollection
    @CollectionTable(name = "user_games", joinColumns = @JoinColumn(name = "profile_id"))
    private Set<String> favoriteGames;

    private Integer followerCount = 0;

    private Integer followingCount = 0;

    private Boolean isVerified = false;

    private String twitchHandle;

    private String youtubeChannel;

    private String discordHandle;

    private String instagramHandle;

    private String twitterHandle;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
