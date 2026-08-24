package com.project.bus_reservation.buses.service;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.request.BusUpdateRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.mapper.BusMapper;
import com.project.bus_reservation.buses.repository.BusesRepository;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BusesService {

    @Autowired
    private BusesRepository busesRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public BusResponse createBus(BusRequest busRequest) {
        Operator operator = operatorRepository.findById(busRequest.getOperatorId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + busRequest.getOperatorId()));

        Bus bus = BusMapper.toEntity(busRequest, operator);
        return BusMapper.toResponseBus(busesRepository.save(bus));
    }

    public List<BusResponse> getAllBuses() {
        return busesRepository.findAll().stream()
                .map(BusMapper::toResponseBus)
                .toList();
    }

    public BusResponse getBusById(Long id) {
        Optional<Bus> bus = busesRepository.findById(id);
        BusResponse Response = null;
        if (bus.isPresent()) {
            Bus _bus = bus.get();
            Response = BusMapper.toResponseBus(_bus);
        }

        return Response;
    }

    public void updateById(Long id, BusUpdateRequest busUpdateRequest) {
//        Optional<Bus> bus = busesRepository.findById(id);
//        Optional<Operator> operator = operatorRepository.findById(busUpdateRequest.getOperatorId());
//        Bus _bus = null;
//        Operator _operator = null;
//
//        if (bus.isPresent()) {
//            _bus = bus.get();
//        } else {
//            throw new ResponseStatusException(NOT_FOUND, "Bus not found with id:" + id);
//        }
//
//        if (operator.isPresent()) {
//            _operator = operator.get();
//        } else {
//            throw new ResponseStatusException(NOT_FOUND, "Operator not fount with id: " + busUpdateRequest.getOperatorId());
//        }
//
//        Bus updatedBus = BusMapper.toUpdate(busUpdateRequest, _bus, _operator);
//        busesRepository.save(updatedBus);


        Optional<Bus> bus = busesRepository.findById(id);

        Bus _bus = null;
        Operator _operator = null;

        if (bus.isPresent()) {
            _bus = bus.get();
        } else {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    "Bus not found with id: " + id
            );
        }

        // Only search for operator if operatorId is provided
        if (busUpdateRequest.getOperatorId() != null) {

            Optional<Operator> operator =
                    operatorRepository.findById(busUpdateRequest.getOperatorId());

            if (operator.isPresent()) {
                _operator = operator.get();
            } else {
                throw new ResponseStatusException(
                        NOT_FOUND,
                        "Operator not found with id: " + busUpdateRequest.getOperatorId()
                );
            }
        }

        Bus updatedBus = BusMapper.toUpdate(busUpdateRequest, _bus, _operator);

        busesRepository.save(updatedBus);
    }

    public void deleteBusById(Long id) {
        busesRepository.deleteById(id);
    }
}