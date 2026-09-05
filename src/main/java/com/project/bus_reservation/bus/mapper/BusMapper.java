package com.project.bus_reservation.bus.mapper;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.enums.BusStatus;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.mapper.OperatorMapper;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.mapper.RouteMapper;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusMapper {

    public static Bus toBusEntity(Operator operator, Route route, BusCreateRequest busCreateRequest) {
        Bus bus = new Bus();
        bus.setBusNumber(busCreateRequest.getBusNumber());
        bus.setBusName(busCreateRequest.getBusName());
        bus.setBusType(busCreateRequest.getBusType());
        bus.setTotalSeats(busCreateRequest.getTotalSeats());
        bus.setStatus(BusStatus.ACTIVE);
        bus.setOperator(operator);

        if (route != null) {
            bus.setRoute(route);
        }

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

    public static BusResponse toBusResponse(Bus bus) {
        Operator operator = bus.getOperator();
        OperatorResponse operatorResponse = OperatorMapper.toOperatorResponse(operator);

        List<BusResponse.SeatResponse> seatResponses = bus.getSeats()
                .stream()
                .map(BusMapper::toSeatResponse)
                .toList();

        Route route = bus.getRoute();
        RouteResponse routeResponse = null;
        if (route != null) {
            routeResponse = RouteMapper.toRouteResponse(route);
        }

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

    public static BusResponse.SeatResponse toSeatResponse(Seat seat) {
        return new BusResponse.SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }

    public static Bus updateBusTransfer(Operator transferOperator, Bus bus) {
        bus.setOperator(transferOperator);
        return bus;
    }
}