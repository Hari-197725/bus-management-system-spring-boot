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
import com.google.cloud.Timestamp;
import com.project.bus_reservation.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BusMapper {

    public static Bus toBusEntity(String operatorId, String routeId, BusCreateRequest busCreateRequest) {
        Bus bus = new Bus();
        bus.setId(UUID.randomUUID().toString());
        bus.setBusNumber(busCreateRequest.getBusNumber());
        bus.setBusName(busCreateRequest.getBusName());
        bus.setBusType(busCreateRequest.getBusType());
        bus.setTotalSeats(busCreateRequest.getTotalSeats());
        bus.setStatus(BusStatus.ACTIVE);
        bus.setCreatedAt(Timestamp.now());
        bus.setModifiedAt(Timestamp.now());
        bus.setOperatorId(operatorId);
        bus.setRouteId(routeId);

        if (busCreateRequest.getSeats() != null) {
            List<Seat> seats = busCreateRequest.getSeats().stream()
                    .map(seatReq -> {
                        Seat seat = new Seat();
                        String uniqueSeatId = UUID.randomUUID().toString();
                        seat.setId(uniqueSeatId);
                        seat.setSeatId(uniqueSeatId);
                        seat.setSeatNumber(seatReq.getSeatNumber());
                        seat.setSeatType(seatReq.getSeatType());
                        seat.setSeatStatus(SeatStatus.AVAILABLE);
                        seat.setBusId(bus.getId());
                        return seat;
                    })
                    .toList();
            bus.setSeats(seats);
        }

        return bus;
    }

    public static BusResponse toBusResponse(Bus bus) {
        return toBusResponse(bus, null, null);
    }

    public static BusResponse toBusResponse(Bus bus, Operator operator, Route route) {
        OperatorResponse operatorResponse = null;
        if (operator != null) {
            operatorResponse = OperatorMapper.toOperatorResponse(operator);
        }

        List<BusResponse.SeatResponse> seatResponses = bus.getSeats() != null
                ? bus.getSeats().stream().map(BusMapper::toSeatResponse).toList()
                : List.of();

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
                DateTimeUtil.toLocalDateTime(bus.getCreatedAt()),
                DateTimeUtil.toLocalDateTime(bus.getModifiedAt())
        );
    }

    public static BusResponse.SeatResponse toSeatResponse(Seat seat) {
        String seatId = seat.getSeatId() != null ? seat.getSeatId() : seat.getId();
        return new BusResponse.SeatResponse(seatId, seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }

    public static Bus updateBusTransfer(Operator transferOperator, Bus bus) {
        if (transferOperator != null) {
            bus.setOperatorId(transferOperator.getId());
        }
        return bus;
    }
}