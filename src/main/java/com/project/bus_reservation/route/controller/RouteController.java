package com.project.bus_reservation.route.controller;

import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.service.RouteService;
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

@Tag(name = "Route Management", description = "Operations for creating and retrieving routes for an operator")
@RestController
@RequestMapping("/api/v1/operator/{operatorId}/route")
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
                description = "Not Found - Operator or Route not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Route already exists",
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
public class RouteController {
    @Autowired
    private RouteService routeService;

    @Operation(summary = "Create a route", description = "Creates a new travel route under the specified operator")
    @ApiResponse(responseCode = "201", description = "Route created successfully")
    @PostMapping
    public ResponseEntity<Void> createRoute(
            @Parameter(description = "Unique identifier of the operator")
            @PathVariable String operatorId,
            @Valid @RequestBody RouteCreateRequest routeCreateRequest) {
        routeService.createRoute(operatorId, routeCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all routes for an operator", description = "Retrieves all routes configured for the specified operator")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all routes",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = RouteResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes(
            @Parameter(description = "Unique identifier of the operator")
            @PathVariable String operatorId) {
        return new ResponseEntity<>(routeService.getAllRoutes(operatorId), HttpStatus.OK);
    }

    @Operation(summary = "Get route by ID", description = "Retrieves details of a specific route under an operator")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved route details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RouteResponse.class)
            )
    )
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(
            @Parameter(description = "Unique identifier of the operator")
            @PathVariable String operatorId,
            @Parameter(description = "Unique identifier of the route")
            @PathVariable String routeId) {
        return new ResponseEntity<>(routeService.getRouteById(operatorId, routeId), HttpStatus.OK);
    }
}