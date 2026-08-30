package com.project.bus_reservation.buses.service;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.request.BusUpdateRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.mapper.BusMapper;
import com.project.bus_reservation.buses.repository.BusesRepository;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.route.mapper.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BusesService {

    @Autowired
    private BusesRepository busesRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public BusResponse createBus(BusRequest busRequest, Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        Bus bus = BusMapper.toBusEntity(busRequest, operator);
        return BusMapper.toBusResponse(busesRepository.save(bus));
    }

    public List<BusResponse> getAllBuses(Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<BusResponse> busResponses = new ArrayList<>();
        List<Bus> buses = operator.getBuses();
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


    public void updateBusByBusId(Long operatorId, Long busId, BusUpdateRequest busUpdateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        if (busUpdateRequest.getBusType() != null && busUpdateRequest.getOperatorId() == null) {
            List<Bus> buses = operator.getBuses();
            for (Bus bus : buses) {
                if (bus.getId().equals(busId)) {
                    Bus _bus = BusMapper.toUpdate(busUpdateRequest, bus);
                    busesRepository.save(_bus);
                    break;
                }
            }
        }


    }

    public void deleteBusById(Long operatorId, Long busId) {
//        This method is working partially so its not correct.

//        Operator operator = operatorRepository.findById(operatorId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));
//
//        List<Bus> buses = operator.getBuses();
//        for (Bus bus : buses) {
//            if (bus.getId().equals(busId)) {
//                busesRepository.deleteById(busId);
//                buses.remove(bus);
//                break;
//            }
//        }

//        This method is said by AI but it has some internal issues

//        Bus bus = busesRepository.findByIdAndoperatorId(operatorId, busId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Bus not found with id: " + busId));
//
//        busesRepository.delete(bus);
    }
}