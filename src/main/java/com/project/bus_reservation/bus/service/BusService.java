package com.project.bus_reservation.bus.service;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.mapper.RouteMapper;
import com.project.bus_reservation.route.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private com.project.bus_reservation.seats.repository.SeatRepository seatRepository;

    public void createBus(String operatorId, BusCreateRequest busCreateRequest) {
        Operator operator = operatorRepository.findById(operatorId).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        String routeId = null;
        if (busCreateRequest.getRouteId() != null) {
            Route r = routeRepository.findById(busCreateRequest.getRouteId()).block();
            if (r == null || r.getOperatorId() == null || !operatorId.equals(r.getOperatorId())) {
                throw new NotFoundException(ErrorCode.ROUTE_NOT_FOUND, "Route id " + busCreateRequest.getRouteId() + " not found for operator: " + operatorId);
            }
            routeId = r.getId();
        }

        Bus bus = BusMapper.toBusEntity(operatorId, routeId, busCreateRequest);
        busRepository.save(bus).block();
        if (bus.getSeats() != null && !bus.getSeats().isEmpty()) {
            seatRepository.saveAll(bus.getSeats()).collectList().block();
        }
    }

    public List<BusResponse> getAllBuses(String operatorId) {
        Operator operator = operatorRepository.findById(operatorId).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        List<Bus> operatorBuses = busRepository.findByOperatorId(operatorId).collectList().block();
        if (operatorBuses == null || operatorBuses.isEmpty()) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "No Buses found for operator with id: " + operatorId);
        }

        List<BusResponse> busResponses = new ArrayList<>();
        for (Bus bus : operatorBuses) {
            Route route = null;
            if (bus.getRouteId() != null) {
                route = routeRepository.findById(bus.getRouteId()).block();
            }
            busResponses.add(BusMapper.toBusResponse(bus, operator, route));
        }

        return busResponses;
    }

    public BusResponse getBusById(String operatorId, String busId) {
        Operator operator = operatorRepository.findById(operatorId).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        Bus bus = busRepository.findById(busId).block();
        if (bus == null || bus.getOperatorId() == null || !operatorId.equals(bus.getOperatorId())) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus not found with id: " + busId + " for operator id: " + operatorId);
        }

        Route route = null;
        if (bus.getRouteId() != null) {
            route = routeRepository.findById(bus.getRouteId()).block();
        }

        return BusMapper.toBusResponse(bus, operator, route);
    }

    public RouteResponse getRouteByBusId(String operatorId, String busId) {
        Operator operator = operatorRepository.findById(operatorId).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        Bus bus = busRepository.findById(busId).block();
        if (bus == null || bus.getOperatorId() == null || !operatorId.equals(bus.getOperatorId())) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus with id " + busId + " not found for operator id: " + operatorId);
        }

        if (bus.getRouteId() != null) {
            Route route = routeRepository.findById(bus.getRouteId()).block();
            if (route != null) {
                return RouteMapper.toRouteResponse(route);
            }
        }
        throw new NotFoundException(ErrorCode.ROUTE_NOT_FOUND, "Route not found for Bus id: " + busId);
    }
}