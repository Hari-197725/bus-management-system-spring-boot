package com.project.bus_reservation.buses.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.bus_reservation.buses.enums.BusStatus;
import com.project.bus_reservation.buses.enums.BusType;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.seats.dto.response.SeatResponse;
import com.project.bus_reservation.seats.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusResponse {

    private Long id;
    private Integer busNo;
    private BusType busType;
    private String busName;
    private Integer totalSeats;
    private BusStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OperatorResponse operator;
    private List<SeatResponse> seats;
}