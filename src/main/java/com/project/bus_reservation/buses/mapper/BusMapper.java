package com.project.bus_reservation.buses.mapper;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.request.BusUpdateRequest;
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

    public static Bus toBusEntity(BusRequest busrequest, Operator operator) {
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

    public static BusResponse.SeatResponse toSeatResponse(Seat seat) {
        return new BusResponse.SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getSeatStatus());
    }

    public static Bus toUpdate(BusUpdateRequest busUpdateRequest, Bus bus) {
        if (busUpdateRequest.getBusType() != null) {
            bus.setBusType(busUpdateRequest.getBusType());
        }

//        if(busUpdateRequest.getRouteId()!= null){
//            bus.setRoute(busUpdateRequest.getRouteId());
//        }
//        if(busUpdateRequest.getOperatorId()!=null){
//            bus.setOperator(busUpdateRequest.getOperatorId());
//        }

//        if(operator != null){
//            bus.setOperator(operator);
//        }

        return bus;
    }
}