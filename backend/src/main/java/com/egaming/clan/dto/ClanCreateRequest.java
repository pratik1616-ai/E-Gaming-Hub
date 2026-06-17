package com.egaming.clan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClanCreateRequest {
    @NotBlank(message = "Clan name is required")
    private String clanName;

    private String clanTag;
    private String description;
    private String logo;
    private Boolean isPublic = true;
}
