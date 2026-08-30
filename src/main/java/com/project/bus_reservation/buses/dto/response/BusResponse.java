package com.project.bus_reservation.buses.dto.response;

import com.project.bus_reservation.buses.enums.BusStatus;
import com.project.bus_reservation.buses.enums.BusType;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.seats.enums.SeatStatus;
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
    private String busName;
    private BusType busType;
    private OperatorResponse operator;
    private List<SeatResponse> seats;
    private RouteResponse route;
    private Integer totalSeats;
    private BusStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SeatResponse {

        private Long id;
        private Integer seatNumber;
        private SeatType seatType;
        private SeatStatus seatStatus;
    }
}