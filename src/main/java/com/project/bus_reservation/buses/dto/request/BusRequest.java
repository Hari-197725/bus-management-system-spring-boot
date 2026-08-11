package com.project.bus_reservation.buses.dto.request;

import com.project.bus_reservation.buses.enums.BusStatus;
import com.project.bus_reservation.buses.enums.BusType;
import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
public class BusRequest {

    @NotNull
    @Positive
    @Min(6)
    private Integer busNumber;

    @NotBlank
    private String busName;

    @NotNull
    private BusType busType;

    @NotNull
    private Long operatorId;

    @NotNull
    @Min(18)
    private Integer totalSeats;

    @NotNull
    private BusStatus status;

    @Valid
    @NotEmpty
    private List<SeatRequest> seats;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SeatRequest {

        @NotNull
        @Positive
        private Integer seatNumber;

        @NotNull
        private SeatType seatType;
    }
}
