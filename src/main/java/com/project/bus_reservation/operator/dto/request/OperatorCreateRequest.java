package com.project.bus_reservation.operator.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OperatorCreateRequest {

    @NotBlank
    private String operatorName;
}
