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

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BusesService {

    @Autowired
    private BusesRepository busesRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private BusMapper busMapper;

    public List<BusResponse> getAllBuses() {
        return busesRepository.findAll().stream()
                .map(busMapper::toResponse)
                .toList();
    }

    public BusResponse getBusById(Long id) {
        Bus bus = busesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Bus not found with id: " + id));
        return busMapper.toResponse(bus);
    }

    public BusResponse createBus(BusRequest request) {
        if (request.getSeats().size() != request.getTotalSeats()) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "Seat count (" + request.getSeats().size() + ") does not match totalSeats (" + request.getTotalSeats() + ")");
        }
        Operator operator = operatorRepository.findById(request.getOperatorId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + request.getOperatorId()));
        Bus bus = busMapper.toEntity(request, operator);
        return busMapper.toResponse(busesRepository.save(bus));
    }
}
