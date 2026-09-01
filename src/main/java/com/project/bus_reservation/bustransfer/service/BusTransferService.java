package com.project.bus_reservation.bustransfer.service;

import com.project.bus_reservation.bustransfer.dto.request.BusTransferUpdateRequest;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
@Service
public class BusTransferService {
    @Autowired
    OperatorRepository operatorRepository;

    public void updateBusTransfer(Long operatorId, BusTransferUpdateRequest busTransferUpdateRequest) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Operator not found with id: " + operatorId));


        List<Bus> busList = operator.getBuses();
        Bus _bus = null;
        for(Bus bus : busList){
            if(bus.getId().equals(busTransferUpdateRequest.getBusId())){
                _bus = bus;
            }else{
                throw new ResponseStatusException(BAD_REQUEST, "Bus id not found with in operator: " + busTransferUpdateRequest.getBusId());
            }
        }

        Operator transferOperator  = operatorRepository.findById(busTransferUpdateRequest.getTransferOperatorId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Operator not found in all operators: " + busTransferUpdateRequest.getTransferOperatorId()));

        if(_bus!=null){
        Bus bus = BusMapper.updateBusTransfer(transferOperator, _bus);
        }

     }


}