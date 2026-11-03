package com.project.bus_reservation.operator.mapper;

import com.google.cloud.Timestamp;
import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OperatorMapper {

    public static Operator toOperatorEntity(OperatorCreateRequest operatorCreateRequest) {
        Operator operator = new Operator();
        operator.setId(UUID.randomUUID().toString());
        operator.setOperatorName(operatorCreateRequest.getOperatorName());
        operator.setJoinedAt(Timestamp.now());
        return operator;
    }

    public static OperatorResponse toOperatorResponse(Operator operator) {
        return new OperatorResponse(
                operator.getId(),
                operator.getOperatorName(),
                DateTimeUtil.toLocalDateTime(operator.getJoinedAt())
        );
    }

}
