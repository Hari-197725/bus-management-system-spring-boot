package com.project.bus_reservation.bus.controller;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.service.BusService;
import com.project.bus_reservation.config.security.AdminGuard;
import com.project.bus_reservation.config.security.CurrentUser;
import com.project.bus_reservation.config.security.FirebaseUserDetails;
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

@Tag(name = "Bus Management", description = "Operations for managing buses under an operator")
@RestController
@RequestMapping("/api/v1/operator/{operatorId}/bus")
@ApiResponses(value = {
                @ApiResponse(responseCode = "400", description = "Bad Request - Invalid request body or parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized - No valid Firebase token provided", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden - Only admin can perform this action", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "404", description = "Not Found - Operator or Bus not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "409", description = "Conflict - Bus already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "500", description = "Internal Server Error - Unexpected server issue", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
})
public class BusController {

        @Autowired
        BusService busService;

        @Autowired
        AdminGuard adminGuard;

        // ADMIN ONLY — Only admin can register a new bus under an operator
        @Operation(summary = "Create a new bus", description = "Admin only: Registers a new bus under the given operator")
        @ApiResponse(responseCode = "201", description = "Bus created successfully")
        @PostMapping
        public ResponseEntity<Void> createBus(
                        @Parameter(hidden = true) @CurrentUser FirebaseUserDetails currentUser,
                        @Parameter(description = "Unique identifier of the operator") @PathVariable String operatorId,
                        @Valid @RequestBody BusCreateRequest busCreateRequest) {
                adminGuard.requireAdmin(currentUser);
                busService.createBus(operatorId, busCreateRequest);
                return new ResponseEntity<>(HttpStatus.CREATED);
        }

        // PUBLIC — Anyone can see all buses under an operator
        @Operation(summary = "Get all buses by operator", description = "Public: Retrieves all buses belonging to the specified operator")
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all buses", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BusResponse.class))))
        @GetMapping
        public ResponseEntity<List<BusResponse>> getAllBuses(
                        @Parameter(description = "Unique identifier of the operator") @PathVariable String operatorId) {
                return new ResponseEntity<>(busService.getAllBuses(operatorId), HttpStatus.OK);
        }

        // PUBLIC — Anyone can see a specific bus's details
        @Operation(summary = "Get bus by ID", description = "Public: Retrieves details of a specific bus under an operator")
        @ApiResponse(responseCode = "200", description = "Successfully retrieved bus details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BusResponse.class)))
        @GetMapping("/{busId}")
        public ResponseEntity<BusResponse> getBusById(
                        @Parameter(description = "Unique identifier of the operator") @PathVariable String operatorId,
                        @Parameter(description = "Unique identifier of the bus") @PathVariable String busId) {
                return new ResponseEntity<>(busService.getBusById(operatorId, busId), HttpStatus.OK);
        }

        // PUBLIC — Anyone can see the route assigned to a bus
        @Operation(summary = "Get route for a bus", description = "Public: Retrieves the assigned route for a specific bus")
        @ApiResponse(responseCode = "200", description = "Successfully retrieved route for the bus", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RouteResponse.class)))
        @GetMapping("/{busId}/route")
        public ResponseEntity<RouteResponse> getRouteByBusId(
                        @Parameter(description = "Unique identifier of the operator") @PathVariable String operatorId,
                        @Parameter(description = "Unique identifier of the bus") @PathVariable String busId) {
                return new ResponseEntity<>(busService.getRouteByBusId(operatorId, busId), HttpStatus.OK);
        }
}
