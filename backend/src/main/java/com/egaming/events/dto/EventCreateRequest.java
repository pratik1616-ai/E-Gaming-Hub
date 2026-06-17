package com.egaming.events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class EventCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String bannerImage;

    @NotBlank(message = "Game name is required")
    private String gameName;

    @NotBlank(message = "Event type is required")
    private String eventType;

    @NotBlank(message = "Event format is required")
    private String eventFormat;

    private Integer maxParticipants;
    private BigDecimal entryFee;
    private BigDecimal prizePool;
    private String startDate;
    private String endDate;
    private String registrationDeadline;
}
