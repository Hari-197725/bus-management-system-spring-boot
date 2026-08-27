package com.project.bus_reservation.route.service;

import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.dto.request.RouteRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RouteService {

    @Autowired
    private OperatorRepository operatorRepository;

    public RouteResponse createRoute(Long operatorId, RouteRequest routeRequest) {
        Operator operator = operatorRepository.findById(operatorId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));


    }

}
