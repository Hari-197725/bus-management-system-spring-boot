package com.project.bus_reservation.buses.mapper;

import com.project.bus_reservation.buses.dto.request.BusCreateRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.enums.BusStatus;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.mapper.OperatorMapper;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.mapper.RouteMapper;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusMapper {

    public static BusResponse toBusResponse(Bus bus) {
        OperatorResponse operatorResponse = OperatorMapper.toResponse(bus.getOperator());

        List<BusResponse.SeatResponse> seatResponses = bus.getSeats()
                .stream()
                .map(BusMapper::toSeatResponse)
                .toList();

        RouteResponse routeResponse = RouteMapper.toRouteResponse(bus.getRoute());


        return new BusResponse(
                bus.getId(),
                bus.getBusNumber(),
                bus.getBusName(),
                bus.getBusType(),
                operatorResponse,
                seatResponses,
                routeResponse,
                bus.getTotalSeats(),
                bus.getStatus(),
                bus.getCreatedAt(),
                bus.getModifiedAt()
        );
    }

    public static Bus toBusEntity(BusCreateRequest busCreateRequest, Operator operator) {
        Bus bus = new Bus();
        bus.setBusNumber(busCreateRequest.getBusNumber());
        bus.setBusName(busCreateRequest.getBusName());
        bus.setBusType(busCreateRequest.getBusType());
        bus.setTotalSeats(busCreateRequest.getTotalSeats());
        bus.setStatus(BusStatus.ACTIVE);
        bus.setOperator(operator);
        bus.setRoute(null);

        List<Seat> seats = busCreateRequest.getSeats().stream()
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

    public static BusResponse.SeatResponse toSeatResponse(Seat seat) {
        return new BusResponse.SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }

    public static Bus updateBusTransfer(Operator transferOperator, Bus bus){
        bus.setOperator(transferOperator);
        return bus;
    }
}