package com.project.bus_reservation.operator.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Request payload for creating a new operator")
public class OperatorCreateRequest {

    @NotBlank
    @Schema(description = "Name of the bus operator", requiredMode = Schema.RequiredMode.REQUIRED)
    private String operatorName;
}
