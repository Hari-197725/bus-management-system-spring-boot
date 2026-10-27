package com.project.bus_reservation.operator.service;

import com.project.bus_reservation.exception.custom.ConflictException;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.operator.mapper.OperatorMapper;
import com.project.bus_reservation.operator.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    public void createOperator(OperatorCreateRequest operatorCreateRequest) {
        if (operatorRepository.findByOperatorName(operatorCreateRequest.getOperatorName()).block() != null) {
            throw new ConflictException(ErrorCode.OPERATOR_ALREADY_EXISTS, "Operator name already exists");
        }
        Operator operator = OperatorMapper.toOperatorEntity(operatorCreateRequest);
        if (operator.getId() == null) {
            operator.setId(String.valueOf(System.currentTimeMillis()));
            operator.setJoinedAt(LocalDateTime.now());
        }
        operatorRepository.save(operator).block();
    }

    public List<OperatorResponse> getAllOperators() {
        List<Operator> list = operatorRepository.findAll().collectList().block();
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream()
                .map(OperatorMapper::toOperatorResponse)
                .toList();
    }

    public OperatorResponse getOperatorById(Long operatorId) {
        Operator operator = operatorRepository.findById(String.valueOf(operatorId)).block();
        if (operator == null) {
            throw new NotFoundException(ErrorCode.OPERATOR_NOT_FOUND, "Operator not found with id: " + operatorId);
        }

        return OperatorMapper.toOperatorResponse(operator);
    }
}