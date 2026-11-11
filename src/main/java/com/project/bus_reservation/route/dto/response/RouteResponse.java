package com.project.bus_reservation.route.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Route details response")
public class RouteResponse {

    @Schema(description = "Unique identifier of the route")
    private Long id;

    @Schema(description = "Source city/location")
    private String source;

    @Schema(description = "Destination city/location")
    private String destination;

    @Schema(description = "Distance in kilometers")
    private double distance;

    @Schema(description = "Estimated travel duration in minutes")
    private Integer estimatedDuration;

    @Schema(description = "ID of the operator this route belongs to")
    private Long operatorId;

    @Schema(description = "Date and time when the route was created")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the route was last modified")
    private LocalDateTime modifiedAt;
}
