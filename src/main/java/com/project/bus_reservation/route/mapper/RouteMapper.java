package com.project.bus_reservation.route.mapper;

import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RouteMapper {

    public static Route toRouteEntity(Operator operator, RouteCreateRequest routeCreateRequest) {
        Route route = new Route();
        route.setId(UUID.randomUUID().toString());
        route.setSource(routeCreateRequest.getSource());
        route.setDestination(routeCreateRequest.getDestination());
        route.setDistance(routeCreateRequest.getDistance());
        route.setEstimatedDuration(routeCreateRequest.getEstimatedDuration());
        route.setCreatedAt(LocalDateTime.now());
        route.setModifiedAt(LocalDateTime.now());
        route.setOperator(operator);
        return route;
    }

    public static RouteResponse toRouteResponse(Route route) {
        String operatorId = route.getOperator() != null ? route.getOperator().getId() : null;

        return new RouteResponse(
                route.getId(),
                route.getSource(),
                route.getDestination(),
                route.getDistance(),
                route.getEstimatedDuration(),
                operatorId,
                route.getCreatedAt(),
                route.getModifiedAt()
        );
    }
}