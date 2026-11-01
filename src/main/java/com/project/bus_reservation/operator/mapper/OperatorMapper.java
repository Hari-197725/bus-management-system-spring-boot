package com.project.bus_reservation.operator.mapper;

import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.entity.Operator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OperatorMapper {

    public static Operator toOperatorEntity(OperatorCreateRequest operatorCreateRequest) {
        Operator operator = new Operator();
        operator.setOperatorName(operatorCreateRequest.getOperatorName());
        operator.setJoinedAt(LocalDateTime.now());
        return operator;
    }

    public static OperatorResponse toOperatorResponse(Operator operator) {
        return new OperatorResponse(
                operator.getFirebaseUid(),
                operator.getOperatorName(),
                operator.getJoinedAt()
        );
    }

}
