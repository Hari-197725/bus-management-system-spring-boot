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

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        boolean isAvailable = false;
        for (Route route : routeList) {
            if (route.getId().equals(busCreateRequest.getRouteId())) {
                _route = route;
                isAvailable = true;
                break;
            }
        }

        if (isAvailable) {
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

        BusResponse busResponse = null;
        List<Bus> buses = operator.getBuses();
        for (Bus bus : buses) {
            if (bus.getId().equals(busId)) {
                busResponse = BusMapper.toBusResponse(bus);
            }
        }

        return busResponse;

    }

    public RouteResponse getAllRoutesByBusId(Long operatorId, Long busId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));


        List<Bus> buses = operator.getBuses();
        for (Bus bus : buses) {
            if (bus.getId().equals(busId)) {
                return RouteMapper.toRouteResponse(bus.getRoute());
            }
        }

        return null;
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