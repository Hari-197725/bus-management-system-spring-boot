package com.project.bus_reservation.operator.service;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.repository.BusesRepository;
import com.project.bus_reservation.operator.dto.request.OperatorRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.mapper.OperatorMapper;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private BusesRepository busesRepository;

    @Autowired
    private RouteRepository routeRepository;

    public void createOperator(OperatorRequest request) {
        Operator operator = OperatorMapper.toEntity(request);
        OperatorMapper.toResponse(operatorRepository.save(operator));
    }

    public List<OperatorResponse> getAllOperators() {
        return operatorRepository.findAll().stream()
                .map(OperatorMapper::toResponse)
                .toList();
    }

    public OperatorResponse getOperatorById(Long id) {
        Operator operator = operatorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + id));

        return OperatorMapper.toResponse(operator);
    }

    @Transactional
    public void deleteOperatorById(Long operatorId) {
//        Manual method with detail steps
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

//        Delete buses
        List<Bus> busList = operator.getBuses();
        for (Bus bus : busList) {
            if (bus.getSeats() != null) {
//                remove seats
                bus.getSeats().clear();
            }

//            remove route relationship
            Route route = bus.getRoute();
            if (route != null) {
                bus.setRoute(null);
            }

//          Delete bus
            busesRepository.delete(bus);
        }

//            Delete routes
        List<Route> routeList = operator.getRoutes();

        for (Route route : routeList) {
            route.setBus(null);
            routeRepository.delete(route);
        }

//        Finally delete operator
        operatorRepository.delete(operator);


//        inbuilt optimized way

//        Operator operator = operatorRepository.findById(operatorId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
//
//        operatorRepository.delete(operator);
//        operatorRepository.deleteById(operatorId);
    }


}