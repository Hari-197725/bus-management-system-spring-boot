package com.project.bus_reservation.operator.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Operator details response")
public class OperatorResponse {

    @Schema(description = "Unique identifier of the operator")
    private Long id;

    @Schema(description = "Name of the bus operator")
    private String operatorName;

    @Schema(description = "Date and time when the operator was registered")
    private LocalDateTime joinedAt;
}
