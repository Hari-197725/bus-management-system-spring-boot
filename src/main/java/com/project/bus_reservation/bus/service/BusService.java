package com.project.bus_reservation.bus.service;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.bus.repository.BusesRepository;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.mapper.RouteMapper;
import com.project.bus_reservation.route.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class BusService {
    @Autowired
    private BusesRepository busesRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RouteRepository routeRepository;

    public BusResponse createBus(Long operatorId, BusCreateRequest busCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Route> routeList = operator.getRoutes();
        Route _route = null;
        boolean isNotAvailable = true;

        for (Route route : routeList) {
            if (route.getId().equals(busCreateRequest.getRouteId())) {
                _route = route;
                isNotAvailable = false;
                break;
            }
        }

        if (isNotAvailable) {
            throw new ResponseStatusException(BAD_REQUEST, "Route id not found with in operator: " + busCreateRequest.getRouteId());
        }

        Bus bus = BusMapper.toBusEntity(operator, _route, busCreateRequest);
        return BusMapper.toBusResponse(busesRepository.save(bus));
    }

    public List<BusResponse> getAllBuses(Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Bus> buses = operator.getBuses();
        List<BusResponse> busResponses = new ArrayList<>();
        for (Bus bus : buses) {
            busResponses.add(BusMapper.toBusResponse(bus));
        }

        return busResponses;
    }

    public BusResponse getBusById(Long operatorId, Long busId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Bus> buses = operator.getBuses();
        BusResponse busResponse = null;
        boolean isNotAvailable = true;

        for (Bus bus : buses) {
            if (bus.getId().equals(busId)) {
                busResponse = BusMapper.toBusResponse(bus);
                isNotAvailable = false;
                break;
            }
        }

        if (isNotAvailable) {
            throw new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        return busResponse;
    }

    public RouteResponse getAllRouteByBusId(Long operatorId, Long busId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Bus> buses = operator.getBuses();
        Bus _bus = null;
        boolean isNotAvailable = true;
        for (Bus bus : buses) {
            if (bus.getId().equals(busId)) {
                _bus = bus;
                isNotAvailable = false;
                break;
            }
        }

        if (isNotAvailable) {
            throw new ResponseStatusException(NOT_FOUND, "Bus id " + busId + " not found with operator id: " + operatorId);
        }

        Route route = _bus.getRoute();
        if (route != null) {
            return RouteMapper.toRouteResponse(route);
        } else {
            throw new ResponseStatusException(NO_CONTENT, "Route not found with bus id: " + busId);
        }
    }

    @Transactional
    public void deleteBusById(Long operatorId, Long busId) {
        Bus bus = busesRepository.findByIdAndOperatorId(busId, operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Bus not found with id: " + busId + " for operator: " + operatorId));

        Route route = bus.getRoute();
        if (route != null) {
            bus.setRoute(null);
            routeRepository.delete(route);
        }

        bus.getSeats().clear();
        busesRepository.delete(bus);
    }
}