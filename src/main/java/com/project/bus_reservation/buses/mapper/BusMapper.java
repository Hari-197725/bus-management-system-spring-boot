package com.project.bus_reservation.buses.mapper;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.request.BusUpdateRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.enums.BusStatus;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class BusMapper {

    public static BusResponse toResponseBus(Bus bus) {
        List<BusResponse.SeatResponse> seatResponses = bus.getSeats()
                .stream()
                .map(BusMapper::toResponseSeat)
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


    public static Bus toEntity(BusRequest busrequest, Operator operator) {
        Bus bus = new Bus();
        bus.setBusNumber(busrequest.getBusNumber());
        bus.setBusName(busrequest.getBusName());
        bus.setBusType(busrequest.getBusType());
        bus.setTotalSeats(busrequest.getTotalSeats());
        bus.setStatus(BusStatus.ACTIVE);
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

    public static BusResponse.SeatResponse toResponseSeat(Seat seat) {
        return new BusResponse.SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }

    public static Bus toUpdate(BusUpdateRequest busUpdateRequest, Bus bus, Operator operator){
        if (busUpdateRequest.getBusType() != null) {
        bus.setBusType(busUpdateRequest.getBusType());
        }

        if(operator!=null){
        bus.setOperator(operator);
        }

        return bus;
    }

}