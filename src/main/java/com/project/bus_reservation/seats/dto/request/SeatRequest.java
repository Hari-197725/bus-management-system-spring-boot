package com.project.bus_reservation.seats.dto.request;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeatRequest {

    @NotNull
    @Positive
    private Integer seatNumber;

    @NotNull
    private SeatType seatType;

    @NotNull
    private SeatStatus seatStatus;

    @Valid
    @NotEmpty
    private BusRequest busRequests;
}
