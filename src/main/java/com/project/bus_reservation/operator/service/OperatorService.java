package com.project.bus_reservation.operator.service;

import com.project.bus_reservation.exception.ConflictException;
import com.project.bus_reservation.exception.NotFoundException;
import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.mapper.OperatorMapper;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    public void createOperator(OperatorCreateRequest operatorCreateRequest) {
        if (operatorRepository.existsByOperatorName(operatorCreateRequest.getOperatorName())) {
            throw new ConflictException("OPERATOR_NAME_ALREADY_EXISTS", "Operator name already exists");
        }
        operatorRepository.save(OperatorMapper.toOperatorEntity(operatorCreateRequest));
    }

    public List<OperatorResponse> getAllOperators() {
        return operatorRepository.findAll().stream()
                .map(OperatorMapper::toOperatorResponse)
                .toList();
    }

    public OperatorResponse getOperatorById(Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new NotFoundException("OPERATOR_NOT_FOUND", "Operator not found with id: " + operatorId));

        return OperatorMapper.toOperatorResponse(operator);
    }
}