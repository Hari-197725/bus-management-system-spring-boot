package com.project.bus_reservation.route.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request payload for creating a new route")
public class RouteCreateRequest {
    @NotBlank
    @Schema(description = "Source city/location", example = "Chennai", requiredMode = Schema.RequiredMode.REQUIRED)
    private String source;

    @NotBlank
    @Schema(description = "Destination city/location", example = "Bangalore", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destination;

    @NotNull
    @Positive
    @Schema(description = "Distance in kilometers", example = "346.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private double distance;

    @NotNull
    @Positive
    @Schema(description = "Estimated travel duration in minutes", example = "360", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer estimatedDuration;
}
