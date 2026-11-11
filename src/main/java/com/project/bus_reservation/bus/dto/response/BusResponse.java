package com.project.bus_reservation.bus.dto.response;

import com.project.bus_reservation.bus.enums.BusStatus;
import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.enums.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "Bus details response")
public class BusResponse {

    @Schema(description = "Unique identifier of the bus")
    private Long id;

    @Schema(description = "Bus registration number")
    private Integer busNo;

    @Schema(description = "Display name of the bus")
    private String busName;

    @Schema(description = "Type of the bus")
    private BusType busType;

    @Schema(description = "Operator who owns this bus")
    private OperatorResponse operator;

    @Schema(description = "List of seats in the bus")
    private List<SeatResponse> seats;

    @Schema(description = "Route assigned to this bus")
    private RouteResponse route;

    @Schema(description = "Total number of seats")
    private Integer totalSeats;

    @Schema(description = "Current status of the bus")
    private BusStatus status;

    @Schema(description = "Date and time when the bus was created")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the bus was last updated")
    private LocalDateTime updatedAt;

    @Getter
    @AllArgsConstructor
    @Schema(description = "Seat details response")
    public static class SeatResponse {
        @Schema(description = "Unique identifier of the seat")
        private Long id;

        @Schema(description = "Seat number")
        private Integer seatNumber;

        @Schema(description = "Type of the seat")
        private SeatType seatType;

        @Schema(description = "Current booking status of the seat")
        private SeatStatus seatStatus;
    }
}
