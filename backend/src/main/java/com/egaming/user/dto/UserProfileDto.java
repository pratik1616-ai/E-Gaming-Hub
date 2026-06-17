package com.egaming.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long userId;
    private String displayName;
    private String bio;
    private String gamingId;
    private String preferredGame;
    private String country;
    private String phoneNumber;
    private String avatar;
    private String coverImage;
    private String twitchHandle;
    private String youtubeChannel;
    private String discordHandle;
    private Integer rank;
    private Integer totalMatches;
    private Integer totalWins;
    private Double winRate;
}
