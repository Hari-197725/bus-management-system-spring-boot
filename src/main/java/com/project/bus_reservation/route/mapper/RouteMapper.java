package com.project.bus_reservation.route.mapper;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.mapper.OperatorMapper;
import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    public static Route toRouteEntity(Operator operator, Bus bus, RouteCreateRequest routeCreateRequest) {
        Route route = new Route();
        route.setSource(routeCreateRequest.getSource());
        route.setDestination(routeCreateRequest.getDestination());
        route.setDistance(routeCreateRequest.getDistance());
        route.setEstimatedDuration(routeCreateRequest.getEstimatedDuration());
        route.setOperator(operator);
        route.setBus(bus);

        return route;
    }

    public static RouteResponse toRouteResponse(Route route) {
        Long operatorId = OperatorMapper.toResponse(route.getOperator()).getId();
        Long busId = BusMapper.toBusResponse(route.getBus()).getId();


        return new RouteResponse(route.getId(),
                route.getSource(),
                route.getDestination(),
                route.getDistance(),
                route.getEstimatedDuration(),
                operatorId,
                busId,
                route.getCreatedAt(),
                route.getModifiedAt()
        );
    }
}