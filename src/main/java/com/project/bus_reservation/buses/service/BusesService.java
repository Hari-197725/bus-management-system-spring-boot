package com.project.bus_reservation.buses.service;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.request.BusUpdateRequest;
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
        return BusMapper.toResponse(busesRepository.save(bus));
    }

    public List<BusResponse> getAllBuses() {
        return busesRepository.findAll().stream()
                .map(BusMapper::toResponse)
                .toList();
    }

    public BusResponse getBusById(Long id) {
        Optional<Bus> bus = busesRepository.findById(id);
        return BusMapper.toResponse(bus.get());
    }

    public void updateById(Long id, BusUpdateRequest busUpdateRequest){
        Optional<Bus> bus = busesRepository.findById(id);
        Optional<Operator> operator = operatorRepository.findById(busUpdateRequest.getOperatorId());
        if(bus.isPresent()&&operator.isPresent()){
            Bus _bus = bus.get();
            Operator _operator = operator.get();
            Bus updatedBus = BusMapper.toUpdate(busUpdateRequest, _bus, _operator);
            busesRepository.save(updatedBus);
        }
    }

    public void deleteBusById(Long id) {
        busesRepository.deleteById(id);
    }
}