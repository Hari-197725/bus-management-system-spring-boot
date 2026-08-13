package com.project.bus_reservation.buses.mapper;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.seats.dto.response.SeatResponse;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.mapper.SeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusMapper {

    @Autowired
    private SeatMapper seatMapper;

    public BusResponse toResponse(Bus bus) {
        List<SeatResponse> seatResponses = bus.getSeats()
                .stream()
                .map(seatMapper::toResponse)
                .toList();

        OperatorResponse operatorResponse = null;
        if (bus.getOperator() != null) {
            operatorResponse = new OperatorResponse(
                    bus.getOperator().getId(),
                    bus.getOperator().getOperatorName(),
                    bus.getOperator().getJoinedAt()
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

//        List<Seat> seats = busrequest.getSeats().stream()
//                .map(seatReq -> {
//                    Seat seat = new Seat();
//                    seat.setSeatNumber(seatReq.getSeatNumber());
//                    seat.setSeatType(seatReq.getSeatType());
//                    seat.setSeatStatus(SeatStatus.AVAILABLE);
//                    seat.setBus(bus);
//                    return seat;
//                })
//                .toList();
//        bus.setSeats(seats);

        return bus;
    }
}