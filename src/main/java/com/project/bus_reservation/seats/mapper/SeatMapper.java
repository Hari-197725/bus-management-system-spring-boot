package com.project.bus_reservation.seats.mapper;

import com.project.bus_reservation.seats.dto.request.SeatRequest;
import com.project.bus_reservation.seats.dto.response.SeatResponse;
import com.project.bus_reservation.seats.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat toEntity(SeatRequest seatRequest) {
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(seatRequest.getSeatType());
        seat.setSeatStatus(seatRequest.getSeatStatus());

        return seat;
    }

    public SeatResponse toResponse(Seat seat) {
        return new SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }




}
