package com.egaming.streaming.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StreamCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String thumbnail;

    @NotBlank(message = "Game name is required")
    private String gameName;

    private Boolean allowChat = true;
    private Boolean allowGifts = true;
}
