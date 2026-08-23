package com.project.bus_reservation.seats.mapper;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeatMapper {

    public static List<Seat> toEntity(BusRequest busSeatRequest) {
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

    public BusResponse.SeatResponse toResponse(Seat seat) {
        return new BusResponse.SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }

}