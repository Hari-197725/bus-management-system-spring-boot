package com.project.bus_reservation.route.service;

import com.project.bus_reservation.bus.entity.Bus;
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

    public RouteResponse createRoute(Long operatorId, RouteCreateRequest routeCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Bus> busList = operator.getBuses();
        Bus _bus = null;
        boolean isNotAvailable = true;
        for (Bus bus : busList) {
            if (bus.getId().equals(routeCreateRequest.getBusId())) {
                _bus = bus;
                isNotAvailable = false;
                break;
            }
        }

            if (isNotAvailable) {
                throw new ResponseStatusException(BAD_REQUEST, "Bus id not found with in operator: " + routeCreateRequest.getBusId());
            }

        Route route = RouteMapper.toRouteEntity(operator, _bus, routeCreateRequest);
        return RouteMapper.toRouteResponse(routeRepository.save(route));

//        This is not the right way to find a particular thing from list tell about the edge cases to hari
//        for (Bus bus : busList) {
//            if (bus.getId().equals(routeCreateRequest.getBusId())) {
//                _bus = bus;
//            } else {
//                throw new ResponseStatusException(BAD_REQUEST, "Bus id not found with in operator: " + routeCreateRequest.getBusId());
//            }
//        }

//        Ask hari how here _bus is not gonna be null if case the busList can be null or not.
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

    public void deleteRouteById(Long operatorId, Long routeId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId)); // Checking if the operator exist.
        List<Route> routes = operator.getRoutes();
        for (Route route : routes) {
            if (route.getId().equals(routeId)) {
                routeRepository.deleteById(routeId);
                break;
            }
        }
    }


}