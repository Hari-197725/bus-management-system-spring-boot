package com.project.bus_reservation.bus.controller;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.service.BusService;
import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.route.dto.response.RouteResponse;
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
@RequestMapping("/api/v1/operator/{operatorId}/bus")
@Tag(name = "Bus Management")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Resource conflict",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public class BusController {
    @Autowired
    BusService busService;

    @Operation(summary = "Create a new bus", description = "Creates a new bus under the specified operator with seat configuration")
    @ApiResponse(responseCode = "201", description = "Bus created successfully")
    @PostMapping
    public ResponseEntity<Void> createBus(
            @Parameter(description = "ID of the operator")
            @PathVariable Long operatorId,
            @Valid @RequestBody BusCreateRequest busCreateRequest) {
        busService.createBus(operatorId, busCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all buses for an operator", description = "Retrieves all buses belonging to a specific operator")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all buses",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BusResponse.class))))
    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses(
            @Parameter(description = "ID of the operator")
            @PathVariable Long operatorId) {
        return new ResponseEntity<>(busService.getAllBuses(operatorId), HttpStatus.OK);
    }

    @Operation(summary = "Get bus by ID", description = "Retrieves details of a specific bus by its ID under the given operator")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved bus details",
            content = @Content(schema = @Schema(implementation = BusResponse.class)))
    @GetMapping("/{busId}")
    public ResponseEntity<BusResponse> getBusById(
            @Parameter(description = "ID of the operator")
            @PathVariable Long operatorId,
            @Parameter(description = "ID of the bus to retrieve")
            @PathVariable Long busId) {
        return new ResponseEntity<>(busService.getBusById(operatorId, busId), HttpStatus.OK);
    }

    @Operation(summary = "Get route for a bus", description = "Retrieves the route assigned to a specific bus")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved route for the bus",
            content = @Content(schema = @Schema(implementation = RouteResponse.class)))
    @GetMapping("/{busId}/route")
    public ResponseEntity<RouteResponse> getRouteByBusId(
            @Parameter(description = "ID of the operator")
            @PathVariable Long operatorId,
            @Parameter(description = "ID of the bus")
            @PathVariable Long busId) {
        return new ResponseEntity<>(busService.getRouteByBusId(operatorId, busId), HttpStatus.OK);
    }
}
