package com.project.bus_reservation.route.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {

    private String id;
    private String source;
    private String destination;
    private double distance;
    private Integer estimatedDuration;
    private String operatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
