package com.project.bus_reservation.route.service;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.repository.BusesRepository;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RouteService {
    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusesRepository busesRepository;

    public RouteResponse createRoute(Long operatorId, RouteCreateRequest routeCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Bus> busList = operator.getBuses();
        if (routeCreateRequest.getBusId() != null && (busList.isEmpty() || busList.stream().noneMatch(bus -> bus.getId().equals(routeCreateRequest.getBusId())))) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid bus id");
        }

        // Consider bus id available
        Bus bus = null;
        if (routeCreateRequest.getBusId() != null) {
            bus = busesRepository.findById(routeCreateRequest.getBusId()).orElse(null);
        }

        // without busId
        Route route = RouteMapper.toRouteEntity(operator, bus, routeCreateRequest);
        Route _route = routeRepository.save(route);
        return RouteMapper.toRouteResponse(_route);
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

    public RouteResponse getRouteById(Long operatorId, Long routeId){
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Route> routeList = operator.getRoutes();
        RouteResponse routeResponse = null;
        boolean isNotAvailable = true;

        for(Route route : routeList){
            if(route.getId().equals(routeId)){
                routeResponse = RouteMapper.toRouteResponse(route);
                isNotAvailable = false;
                break;
            }
        }

        if(isNotAvailable){
            throw new ResponseStatusException(NOT_FOUND, "Route if not found with operator id: " + routeId);
        }

        return routeResponse;
    }

    public void deleteRouteById(Long operatorId, Long routeId) {

    }


}