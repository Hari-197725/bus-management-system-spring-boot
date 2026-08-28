package com.project.bus_reservation.buses.service;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.mapper.BusMapper;
import com.project.bus_reservation.buses.repository.BusesRepository;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
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

//    public void updateById(Long operatorId, Long busId, BusUpdateRequest busUpdateRequest) {
//
//        Operator givenOperator = operatorRepository.findById(operatorId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));
//
//        List<Bus> buses = givenOperator.getBuses();
//        Operator operator = null;
//        Bus bus1 = null;
//        Bus updatedBus = null;
//
//        for (Bus bus : buses) {
//            if (bus.getId().equals(busId) && busUpdateRequest.getOperatorId() != null) {
//                bus1 = bus;
//                updatedBus = BusMapper.toUpdate(busUpdateRequest, bus);
//            }
//        }


//            operator = operatorRepository.findById(busUpdateRequest.getOperatorId())
//                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not fount with id: " + busUpdateRequest.getOperatorId()));
//        }
//
//        Bus updatedBus = BusMapper.toUpdate(busUpdateRequest, bus, operator);
//        busesRepository.save(updatedBus);


//        Optional<Bus> bus = busesRepository.findById(id);
//
//        Bus _bus = null;
//        Operator _operator = null;
//
//        if (bus.isPresent()) {
//            _bus = bus.get();
//        } else {
//            throw new ResponseStatusException(
//                    NOT_FOUND,
//                    "Bus not found with id: " + id
//            );
//        }
//
//        // Only search for operator if operatorId is provided
//        if (busUpdateRequest.getOperatorId() != null) {
//
//            Optional<Operator> operator =
//                    operatorRepository.findById(busUpdateRequest.getOperatorId());
//
//            if (operator.isPresent()) {
//                _operator = operator.get();
//            } else {
//                throw new ResponseStatusException(
//                        NOT_FOUND,
//                        "Operator not found with id: " + busUpdateRequest.getOperatorId()
//                );
//            }
//        }
//
//        Bus updatedBus = BusMapper.toUpdate(busUpdateRequest, _bus, _operator);
//
//        busesRepository.save(updatedBus);
//    }

    public void deleteBusById(Long operatorId, Long busId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));

        List<Bus> buses = operator.getBuses();
        for (Bus bus : buses) {
            if (bus.getId().equals(busId)) {
                busesRepository.deleteById(busId);
                break;
            }
        }
    }
}