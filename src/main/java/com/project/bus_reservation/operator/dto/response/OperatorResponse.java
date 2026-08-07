package com.project.bus_reservation.operator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OperatorResponse {

    private Long id;
    private String operatorName;
    private LocalDateTime joinedAt;
}
