package com.project.bus_reservation.bus.service;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusDisplayService {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public List<BusResponse> getBusesFromTo(String from, String to) {
        List<Bus> allBuses = busRepository.findAll().collectList().block();
        List<BusResponse> busResponseList = new ArrayList<>();

        if (allBuses != null) {
            for (Bus bus : allBuses) {
                if (bus.getRouteId() != null) {
                    Route route = routeRepository.findById(bus.getRouteId()).block();
                    if (route != null
                            && from.equalsIgnoreCase(route.getSource())
                            && to.equalsIgnoreCase(route.getDestination())) {
                        Operator operator = null;
                        if (bus.getOperatorId() != null) {
                            operator = operatorRepository.findById(bus.getOperatorId()).block();
                        }
                        busResponseList.add(BusMapper.toBusResponse(bus, operator, route));
                    }
                }
            }
        }

        return busResponseList;
    }
}