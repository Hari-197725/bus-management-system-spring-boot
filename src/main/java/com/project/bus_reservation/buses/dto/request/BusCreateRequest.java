package com.project.bus_reservation.buses.dto.request;

import com.project.bus_reservation.buses.enums.BusType;
import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusCreateRequest {

    @NotNull
    @Positive
    @Min(6)
    private Integer busNumber;

    @NotBlank
    private String busName;

    @NotNull
    private BusType busType;

    @NotNull
    @Min(18)
    @Max(30)
    private Integer totalSeats;

    @NotNull
    private List<SeatRequest> seats;

    @Getter
    public static class SeatRequest {

        @NotNull
        @Positive
        private Integer seatNumber;

        @NotNull
        private SeatType seatType;
    }
}