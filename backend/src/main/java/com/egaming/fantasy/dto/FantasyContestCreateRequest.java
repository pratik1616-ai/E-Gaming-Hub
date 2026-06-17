package com.egaming.fantasy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FantasyContestCreateRequest {
    @NotBlank(message = "Contest name is required")
    private String contestName;

    private String description;

    @NotBlank(message = "Match type is required")
    private String matchType;

    @NotNull(message = "Entry fee is required")
    private BigDecimal entryFee;

    @NotNull(message = "Prize pool is required")
    private BigDecimal prizePool;

    private Integer maxParticipants;
}
