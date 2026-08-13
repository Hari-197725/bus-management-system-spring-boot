package com.project.bus_reservation.seats.dto.request;

import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusSeatRequest {

    @Positive
    @NotNull
    private Long busId;

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