package com.project.bus_reservation.route.service;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.exception.NotFoundException;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.mapper.RouteMapper;
import com.project.bus_reservation.route.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RouteService {
    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RouteRepository routeRepository;

    public void createRoute(Long operatorId, RouteCreateRequest routeCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new NotFoundException("OPERATOR_NOT_FOUND", "Operator not found with id: " + operatorId));

        Route route = RouteMapper.toRouteEntity(operator, routeCreateRequest);
        routeRepository.save(route);
    }

    public List<RouteResponse> getAllRoutes(Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new NotFoundException("OPERATOR_NOT_FOUND", "Operator not found with id: " + operatorId));

        List<RouteResponse> routeResponses = new ArrayList<>();
        List<Route> routes = operator.getRoutes();
        for (Route route : routes) {
            routeResponses.add(RouteMapper.toRouteResponse(route));
        }

        return routeResponses;
    }

    public RouteResponse getRouteById(Long operatorId, Long routeId) {
        operatorRepository.findById(operatorId)
                .orElseThrow(() -> new NotFoundException("OPERATOR_NOT_FOUND", "Operator not found with id: " + operatorId));

        Route route = routeRepository.findRouteByOperatorId(operatorId, routeId)
                .orElseThrow(() -> new NotFoundException("ROUTE_NOT_FOUND", "Route with id " + routeId + " not found for operator with id " + operatorId));

        return RouteMapper.toRouteResponse(route);
    }

    public void deleteRouteById(Long operatorId, Long routeId) {
        int deletedRows = routeRepository.deleteRouteByOperatorId(routeId, operatorId);
        if (deletedRows == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Route not found with in operator id: " + operatorId);
        }
    }
}