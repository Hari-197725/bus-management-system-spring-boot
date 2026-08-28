package com.project.bus_reservation.route.mapper;

import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.route.dto.request.RouteRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    public static Route toRouteEntity(Operator operator, RouteRequest routeRequest) {
        Route route = new Route();
        route.setSource(routeRequest.getSource());
        route.setDestination(routeRequest.getDestination());
        route.setDistance(routeRequest.getDistance());
        route.setEstimatedDuration(routeRequest.getEstimatedDuration());
        route.setOperator(operator);

        return route;
    }

    public static RouteResponse toRouteResponse(Route route) {
        return new RouteResponse(route.getId(),
                route.getSource(),
                route.getDestination(),
                route.getDistance(),
                route.getEstimatedDuration(),
                route.getCreatedAt(),
                route.getModifiedAt()
        );
    }
}