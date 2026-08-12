package com.project.bus_reservation.seats.dto.response;

import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatResponse {

    private Long id;
    private Integer seatNumber;
    private SeatType seatType;
    private SeatStatus seatStatus;
}