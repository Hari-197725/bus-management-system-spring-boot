package com.project.bus_reservation.route.controller;

import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.service.RouteService;
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
@RequestMapping("/api/v1/operator/{operatorId}/route")
@Tag(name = "Route Management")
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
public class RouteController {
    @Autowired
    private RouteService routeService;

    @Operation(summary = "Create a new route", description = "Creates a new route under the specified operator with source, destination, distance, and estimated duration")
    @ApiResponse(responseCode = "201", description = "Route created successfully")
    @PostMapping
    public ResponseEntity<Void> createRoute(
            @Parameter(description = "ID of the operator", required = true, example = "1")
            @PathVariable Long operatorId,
            @Valid @RequestBody RouteCreateRequest routeCreateRequest) {
        routeService.createRoute(operatorId, routeCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all routes for an operator", description = "Retrieves all routes belonging to a specific operator")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all routes",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RouteResponse.class))))
    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes(
            @Parameter(description = "ID of the operator", required = true, example = "1")
            @PathVariable Long operatorId) {
        return new ResponseEntity<>(routeService.getAllRoutes(operatorId), HttpStatus.OK);
    }

    @Operation(summary = "Get route by ID", description = "Retrieves details of a specific route by its ID under the given operator")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved route details",
            content = @Content(schema = @Schema(implementation = RouteResponse.class)))
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(
            @Parameter(description = "ID of the operator", required = true, example = "1")
            @PathVariable Long operatorId,
            @Parameter(description = "ID of the route to retrieve", required = true, example = "1")
            @PathVariable Long routeId) {
        return new ResponseEntity<>(routeService.getRouteById(operatorId, routeId), HttpStatus.OK);
    }
}
