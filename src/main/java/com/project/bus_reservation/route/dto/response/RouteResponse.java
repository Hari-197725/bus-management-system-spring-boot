package com.project.bus_reservation.route.dto.response;

import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
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

    private Long id;
    private String source;
    private String destination;
    private double distance;
    private Integer estimatedDuration;
    private Long operatorId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
