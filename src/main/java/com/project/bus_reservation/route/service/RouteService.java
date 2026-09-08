package com.project.bus_reservation.route.service;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.repository.BusRepository;
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

    @Autowired
    private BusRepository busRepository;

    public void createRoute(Long operatorId, RouteCreateRequest routeCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        Route route = RouteMapper.toRouteEntity(operator, routeCreateRequest);
        routeRepository.save(route);
    }

    public List<RouteResponse> getAllRoutes(Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<RouteResponse> routeResponses = new ArrayList<>();
        List<Route> routes = operator.getRoutes();
        for (Route route : routes) {
            routeResponses.add(RouteMapper.toRouteResponse(route));
        }

        return routeResponses;
    }

    public RouteResponse getRouteById(Long operatorId, Long routeId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Route> routeList = operator.getRoutes();
        RouteResponse routeResponse = null;
        boolean isNotAvailable = true;

        for (Route route : routeList) {
            if (route.getId().equals(routeId)) {
                routeResponse = RouteMapper.toRouteResponse(route);
                isNotAvailable = false;
                break;
            }
        }

        if (isNotAvailable) {
            throw new ResponseStatusException(NOT_FOUND, "Route if not found with operator id: " + routeId);
        }

        return routeResponse;
    }

    public void deleteRouteById(Long operatorId, Long routeId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Route> routeList = operator.getRoutes();
        Route _route = null;
        boolean isNotAvailable = true;

        for (Route route : routeList) {
            if (route.getId().equals(routeId)) {
                _route = route;
                isNotAvailable = false;
                break;
            }
        }

        if (isNotAvailable) {
            throw new ResponseStatusException(NOT_FOUND, "Route id " + routeId + "not found with in operator id: " + operatorId);
        }

        List<Bus> bus = _route.getBuses();
        if (bus != null) {
            _route.setBuses(null);
        }

        Operator operator1 = _route.getOperator();
        if (operator1 != null) {
            operator1.setRoutes(null);
            _route.setOperator(null);
        }

        routeRepository.delete(_route);
    }
}