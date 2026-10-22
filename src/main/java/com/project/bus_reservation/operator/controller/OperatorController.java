package com.project.bus_reservation.operator.controller;

import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.service.OperatorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @ApiResponse(responseCode = "201", description = "Operator created successfully")
    @PostMapping
    public ResponseEntity<Void> createOperator(@Valid @RequestBody OperatorCreateRequest operatorCreateRequest) {
        operatorService.createOperator(operatorCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved all operators")
    @GetMapping
    public ResponseEntity<List<OperatorResponse>> getAllOperators() {
        return new ResponseEntity<>(operatorService.getAllOperators(), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved operator details")
    @GetMapping("/{operatorId}")
    public ResponseEntity<OperatorResponse> getOperatorById(@PathVariable Long operatorId) {
        return new ResponseEntity<>(operatorService.getOperatorById(operatorId), HttpStatus.OK);
    }
}
