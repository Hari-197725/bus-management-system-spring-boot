package com.project.bus_reservation.bus.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.service.BusDisplayService;
import com.project.bus_reservation.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Bus Search & Display", description = "Public operations for searching and viewing available buses between locations")
@RestController
@RequestMapping("/api/v1/bus")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request - Invalid query parameters",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found - No buses found for this route",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Request conflict",
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
public class BusDisplayController {
    @Autowired
    BusDisplayService busDisplayService;

    @Operation(summary = "Search buses by route", description = "Retrieves a list of buses running between departure (from) and destination (to) locations")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved buses for the route",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BusResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<BusResponse>> getBusesFromTo(
            @Parameter(description = "Departure city or boarding location")
            @RequestParam String from,
            @Parameter(description = "Destination city or drop-off location")
            @RequestParam String to) {
        return new ResponseEntity<>(busDisplayService.getBusesFromTo(from, to), HttpStatus.OK);
    }
}

