package com.project.bus_reservation.buses.mapper;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.enums.SeatStatus;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.models.Seat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusMapper {

    public BusResponse toResponse(Bus bus) {
        List<BusResponse.SeatResponse> seatResponses = new ArrayList<>();
        if (bus.getSeats() != null) {
            bus.getSeats().forEach(seat -> seatResponses.add(
                    new BusResponse.SeatResponse(
                            seat.getSeatNumber(),
                            seat.getSeatType(),
                            seat.getSeatStatus() == SeatStatus.AVAILABLE
                    )
            ));
        }

        BusResponse.OperatorResponse operatorResponse = null;
        if (bus.getOperator() != null) {
            operatorResponse = new BusResponse.OperatorResponse(
                    bus.getOperator().getId(),
                    bus.getOperator().getOperatorName()
            );
        }

        return new BusResponse(
                bus.getId(),
                bus.getBusNumber(),
                bus.getBusType(),
                bus.getBusName(),
                bus.getTotalSeats(),
                bus.getStatus(),
                bus.getCreatedAt(),
                bus.getModifiedAt(),
                operatorResponse,
                seatResponses
        );
    }

    public Bus toEntity(BusRequest busrequest, Operator operator) {
        Bus bus = new Bus();
        bus.setBusNumber(busrequest.getBusNumber());
        bus.setBusName(busrequest.getBusName());
        bus.setBusType(busrequest.getBusType());
        bus.setTotalSeats(busrequest.getTotalSeats());
        bus.setStatus(busrequest.getStatus());
        bus.setOperator(operator);

        List<Seat> seats = busrequest.getSeats().stream()
                .map(seatReq -> {
                    Seat seat = new Seat();
                    seat.setSeatNumber(seatReq.getSeatNumber());
                    seat.setSeatType(seatReq.getSeatType());
                    seat.setSeatStatus(SeatStatus.AVAILABLE);
                    seat.setBus(bus);
                    return seat;
                })
                .toList();
        bus.setSeats(seats);

        return bus;
    }
}
