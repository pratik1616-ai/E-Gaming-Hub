package com.egaming.fantasy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Data
public class FantasyTeamCreateRequest {
    @NotNull(message = "Contest ID is required")
    private Long contestId;

    @NotBlank(message = "Team name is required")
    private String teamName;

    @NotEmpty(message = "Must select 5 players")
    private Set<Long> playerIds;

    @NotNull(message = "Captain is required")
    private Long captainId;

    @NotNull(message = "Vice captain is required")
    private Long viceCaptainId;
}
