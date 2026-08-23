package com.project.bus_reservation.operator.service;

import com.project.bus_reservation.operator.dto.request.OperatorRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.mapper.OperatorMapper;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorMapper operatorMapper;

    public OperatorResponse createOperator(OperatorRequest request) {
        Operator operator = OperatorMapper.toEntity(request);
        return OperatorMapper.toResponse(operatorRepository.save(operator));
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
}
