package com.project.bus_reservation.operator.mapper;

import com.project.bus_reservation.operator.dto.request.OperatorRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import org.springframework.stereotype.Component;

@Component
public class OperatorMapper {

    public static OperatorResponse toResponse(Operator operator) {
        return new OperatorResponse(
                operator.getId(),
                operator.getOperatorName(),
                operator.getJoinedAt()
        );
    }

    public static Operator toEntity(OperatorRequest operatorRequest) {
        Operator operator = new Operator();
        operator.setOperatorName(operatorRequest.getOperatorName());
        return operator;
    }
}
