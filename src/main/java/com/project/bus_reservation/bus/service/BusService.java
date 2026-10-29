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

import java.time.LocalDateTime;
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

    public void createBus(Long operatorId, BusCreateRequest busCreateRequest) {
        Operator operator = operatorRepository.findById(String.valueOf(operatorId)).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        Route route = null;
        if (busCreateRequest.getRouteId() != null) {
            Route r = routeRepository.findById(String.valueOf(busCreateRequest.getRouteId())).block();
            if (r == null || r.getOperator() == null || !operatorId.equals(r.getOperator().getNumericId())) {
                throw new NotFoundException(ErrorCode.ROUTE_NOT_FOUND, "Route id " + busCreateRequest.getRouteId() + " not found for operator: " + operatorId);
            }
            route = r;
        }

        Bus bus = BusMapper.toBusEntity(operator, route, busCreateRequest);
        if (bus.getId() == null) {
            bus.setId(String.valueOf(System.currentTimeMillis()));
            bus.setCreatedAt(LocalDateTime.now());
        }
        bus.setModifiedAt(LocalDateTime.now());
        busRepository.save(bus).block();
    }

    public List<BusResponse> getAllBuses(Long operatorId) {
        Operator operator = operatorRepository.findById(String.valueOf(operatorId)).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.ROUTE_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        List<Bus> allBuses = busRepository.findAll().collectList().block();
        List<Bus> operatorBuses = new ArrayList<>();
        if (allBuses != null) {
            for (Bus b : allBuses) {
                if (b.getOperator() != null && operatorId.equals(b.getOperator().getNumericId())) {
                    operatorBuses.add(b);
                }
            }
        }

        if (operatorBuses.isEmpty()) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "No Buses found for operator with id: " + operatorId);
        }

        List<BusResponse> busResponses = new ArrayList<>();
        for (Bus bus : operatorBuses) {
            busResponses.add(BusMapper.toBusResponse(bus));
        }

        return busResponses;
    }

    public BusResponse getBusById(Long operatorId, Long busId) {
        Operator operator = operatorRepository.findById(String.valueOf(operatorId)).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        Bus bus = busRepository.findById(String.valueOf(busId)).block();
        if (bus == null || bus.getOperator() == null || !operatorId.equals(bus.getOperator().getNumericId())) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus not found for operator id: " + operatorId);
        }

        return BusMapper.toBusResponse(bus);
    }

    public RouteResponse getRouteByBusId(Long operatorId, Long busId) {
        Operator operator = operatorRepository.findById(String.valueOf(operatorId)).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        Bus bus = busRepository.findById(String.valueOf(busId)).block();
        if (bus == null || bus.getOperator() == null || !operatorId.equals(bus.getOperator().getNumericId())) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus with id " + busId + " not found for operator id: " + operatorId);
        }

        if (bus.getRoute() != null) {
            return RouteMapper.toRouteResponse(bus.getRoute());
        } else {
            throw new NotFoundException(ErrorCode.ROUTE_NOT_FOUND, "Route not found for Bus id: " + busId);
        }
    }
}