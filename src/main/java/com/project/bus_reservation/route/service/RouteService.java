package com.project.bus_reservation.route.service;

import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.mapper.RouteMapper;
import com.project.bus_reservation.route.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {
    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RouteRepository routeRepository;

    public void createRoute(String operatorId, RouteCreateRequest routeCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        Route route = RouteMapper.toRouteEntity(operator, routeCreateRequest);
        routeRepository.save(route).block();
    }

    public List<RouteResponse> getAllRoutes(String operatorId) {
        Operator operator = operatorRepository.findById(operatorId).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        List<Route> allRoutes = routeRepository.findAll().collectList().block();
        List<RouteResponse> routeResponses = new ArrayList<>();
        if (allRoutes != null) {
            for (Route route : allRoutes) {
                if (route.getOperator() != null && operatorId.equals(route.getOperator().getId())) {
                    routeResponses.add(RouteMapper.toRouteResponse(route));
                }
            }
        }

        return routeResponses;
    }

    public RouteResponse getRouteById(String operatorId, String routeId) {
        Operator operator = operatorRepository.findById(operatorId).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        Route route = routeRepository.findById(routeId).block();
        if (route == null || route.getOperator() == null || !operatorId.equals(route.getOperator().getId())) {
            throw new NotFoundException(ErrorCode.ROUTE_NOT_FOUND, "Route with id " + routeId + " not found for operator with id " + operatorId);
        }

        return RouteMapper.toRouteResponse(route);
    }
}