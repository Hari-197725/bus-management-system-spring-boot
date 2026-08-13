package com.project.bus_reservation.seats.mapper;

import com.project.bus_reservation.seats.dto.request.BusSeatRequest;
import com.project.bus_reservation.seats.dto.response.SeatResponse;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeatMapper {

    public static List<Seat> toEntity(BusSeatRequest busSeatRequest) {
        return busSeatRequest.getSeats()
                .stream()
                .map(s -> {
                    Seat seat = new Seat();
                    seat.setSeatNumber(s.getSeatNumber());
                    seat.setSeatType(s.getSeatType());
                    seat.setSeatStatus(SeatStatus.AVAILABLE);
                    return seat;
                })
                .toList();
    }

    public SeatResponse toResponse(Seat seat) {
        return new SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }

}