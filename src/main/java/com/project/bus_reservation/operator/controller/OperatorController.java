package com.project.bus_reservation.operator.controller;

import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator")
@Tag(name = "Operator Management")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Resource conflict (e.g., operator already exists)",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @Operation(summary = "Create a new operator", description = "Registers a new bus operator with the given name")
    @ApiResponse(responseCode = "201", description = "Operator created successfully")
    @PostMapping
    public ResponseEntity<Void> createOperator(@Valid @RequestBody OperatorCreateRequest operatorCreateRequest) {
        operatorService.createOperator(operatorCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all operators", description = "Retrieves a list of all registered bus operators")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all operators",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OperatorResponse.class))))
    @GetMapping
    public ResponseEntity<List<OperatorResponse>> getAllOperators() {
        return new ResponseEntity<>(operatorService.getAllOperators(), HttpStatus.OK);
    }

    @Operation(summary = "Get operator by ID", description = "Retrieves details of a specific operator by their ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved operator details",
            content = @Content(schema = @Schema(implementation = OperatorResponse.class)))
    @GetMapping("/{operatorId}")
    public ResponseEntity<OperatorResponse> getOperatorById(
            @Parameter(description = "ID of the operator to retrieve", required = true)
            @PathVariable Long operatorId) {
        return new ResponseEntity<>(operatorService.getOperatorById(operatorId), HttpStatus.OK);
    }
}
