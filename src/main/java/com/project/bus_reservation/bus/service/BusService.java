package com.project.bus_reservation.bus.service;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.mapper.RouteMapper;
import com.project.bus_reservation.route.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RouteRepository routeRepository;

    public void createBus(Long operatorId, BusCreateRequest busCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        Route route = null;
        if (busCreateRequest.getRouteId() != null) {
            route = routeRepository.findRouteByBusRouteId(busCreateRequest.getRouteId(), operatorId)
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Route id " + busCreateRequest.getRouteId() + " not found with in operator: " + operatorId));
        }

        Bus bus = BusMapper.toBusEntity(operator, route, busCreateRequest);
        busRepository.save(bus);
    }

    public List<BusResponse> getAllBuses(Long operatorId) {
        List<Bus> buses = busRepository.findAllBusesByOperatorId(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));
        List<BusResponse> busResponses = new ArrayList<>();
        for (Bus bus : buses) {
            busResponses.add(BusMapper.toBusResponse(bus));
        }

        return busResponses;
    }

    public BusResponse getBusById(Long operatorId, Long busId) {
        Optional<Bus> bus = busRepository.findBusByOperatorId(operatorId, busId);
        if (bus.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Bus not found with in operator id: " + operatorId);
        }

        return BusMapper.toBusResponse(bus.get());
    }

    public RouteResponse getAllRouteByBusId(Long operatorId, Long busId) {
        Bus bus = busRepository.findBusByOperatorId(operatorId, busId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Bus not found with in operator id: " + operatorId));

        if (bus.getRoute() != null) {
            return RouteMapper.toRouteResponse(bus.getRoute());
        } else {
            throw new ResponseStatusException(BAD_REQUEST, "Route not found with in Bus id: " + busId);
        }
    }

    public void deleteBusById(Long operatorId, Long busId) {
        int deletedRows = busRepository.deleteBusByOperatorId(busId, operatorId);
        if (deletedRows == 0) {
            throw new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId);
        }
    }
}