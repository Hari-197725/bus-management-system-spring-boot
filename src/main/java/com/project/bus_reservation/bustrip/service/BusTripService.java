package com.project.bus_reservation.bustrip.service;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.dto.request.BusTripCreateRequest;
import com.project.bus_reservation.bustrip.mapper.BusTripMapper;
import com.project.bus_reservation.bustrip.repository.BusTripRepository;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BusTripService {
    @Autowired
    BusTripRepository busTripRepository;

    @Autowired
    OperatorRepository operatorRepository;

    public void createBusTrip(Long operatorId, BusTripCreateRequest busTripCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Bus> busList = operator.getBuses();
        Bus _bus = null;
        boolean isBusNotAvailable = true;

        for (Bus bus : busList) {
            if (bus.getId().equals(busTripCreateRequest.getBusId())) {
                _bus = bus;
                isBusNotAvailable = false;
                break;
            }
        }

        if (isBusNotAvailable) {
            throw new ResponseStatusException(NOT_FOUND, "Bus id " + busTripCreateRequest.getBusId() + " not found with in operator: " + busTripCreateRequest.getBusId());
        }

        List<Route> routeList = operator.getRoutes();
        Route _route = null;
        boolean isRouteNotAvailable = true;
        for (Route route : routeList) {
            if (route.getId().equals(busTripCreateRequest.getRouteId())) {
                _route = route;
                isRouteNotAvailable = false;
                break;
            }
        }

        if (isRouteNotAvailable) {
            throw new ResponseStatusException(NOT_FOUND, "Route id " + busTripCreateRequest.getRouteId() + " not found with operator id: " + operatorId);
        }

        busTripRepository.save(BusTripMapper.toBusTripEntity(operator, _bus, _route, busTripCreateRequest));
    }

}
