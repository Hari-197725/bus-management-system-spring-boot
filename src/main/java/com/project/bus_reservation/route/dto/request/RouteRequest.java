package com.project.bus_reservation.route.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteRequest {

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @NotNull
    @Positive
    private double distance;

    @NotNull
    @Positive
    private Integer estimatedDuration;
}