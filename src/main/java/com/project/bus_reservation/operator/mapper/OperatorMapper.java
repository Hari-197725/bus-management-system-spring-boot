package com.project.bus_reservation.operator.mapper;

import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import org.springframework.stereotype.Component;

@Component
public class OperatorMapper {

    public static OperatorResponse toOperatorResponse(Operator operator) {
        return new OperatorResponse(
                operator.getId(),
                operator.getOperatorName(),
                operator.getJoinedAt()
        );
    }

    public static Operator toOperatorEntity(OperatorCreateRequest operatorCreateRequest) {
        Operator operator = new Operator();
        operator.setOperatorName(operatorCreateRequest.getOperatorName());
        return operator;
    }
}
