package com.project.bus_reservation.operator.controller;

import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.operator.dto.request.OperatorCreateRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

@Tag(name = "Operator Management", description = "Operations for registering and retrieving bus operators")
@RestController
@RequestMapping("/api/v1/operator")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request - Invalid request body or parameters",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found - Operator does not exist",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Operator already exists",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error - Unexpected server issue",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
})
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @Operation(summary = "Create a new operator", description = "Registers a new bus operator in the system")
    @ApiResponse(responseCode = "201", description = "Operator created successfully")
    @PostMapping
    public ResponseEntity<Void> createOperator(@Valid @RequestBody OperatorCreateRequest operatorCreateRequest) {
        operatorService.createOperator(operatorCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all operators", description = "Retrieves a list of all registered bus operators")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all operators",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OperatorResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<OperatorResponse>> getAllOperators() {
        return new ResponseEntity<>(operatorService.getAllOperators(), HttpStatus.OK);
    }

    @Operation(summary = "Get operator by ID", description = "Retrieves details of a specific operator by ID")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved operator details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OperatorResponse.class)
            )
    )
    @GetMapping("/{operatorId}")
    public ResponseEntity<OperatorResponse> getOperatorById(
            @Parameter(description = "Unique identifier of the operator")
            @PathVariable String operatorId) {
        return new ResponseEntity<>(operatorService.getOperatorById(operatorId), HttpStatus.OK);
    }
}

