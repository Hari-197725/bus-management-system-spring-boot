package com.project.bus_reservation.route.mapper;

import com.google.cloud.Timestamp;
import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RouteMapper {

    public static Route toRouteEntity(String operatorId, RouteCreateRequest routeCreateRequest) {
        Route route = new Route();
        route.setId(UUID.randomUUID().toString());
        route.setSource(routeCreateRequest.getSource());
        route.setDestination(routeCreateRequest.getDestination());
        route.setDistance(routeCreateRequest.getDistance());
        route.setEstimatedDuration(routeCreateRequest.getEstimatedDuration());
        route.setCreatedAt(Timestamp.now());
        route.setModifiedAt(Timestamp.now());
        route.setOperatorId(operatorId);
        return route;
    }

    public static RouteResponse toRouteResponse(Route route) {
        return new RouteResponse(
                route.getId(),
                route.getSource(),
                route.getDestination(),
                route.getDistance(),
                route.getEstimatedDuration(),
                route.getOperatorId(),
                DateTimeUtil.toLocalDateTime(route.getCreatedAt()),
                DateTimeUtil.toLocalDateTime(route.getModifiedAt())
        );
    }
}