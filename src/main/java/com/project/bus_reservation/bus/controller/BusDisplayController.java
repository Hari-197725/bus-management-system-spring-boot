package com.project.bus_reservation.bus.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.service.BusDisplayService;
import com.project.bus_reservation.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
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

@RestController
@RequestMapping("/api/v1/bus")
@Tag(name = "Bus Display")
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
public class BusDisplayController {
    @Autowired
    BusDisplayService busDisplayService;

    @Operation(summary = "Search buses by route", description = "Finds all available buses travelling from source to destination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved buses for the route",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BusResponse.class))))
    @GetMapping
    public ResponseEntity<List<BusResponse>> getBusesFromTo(
            @Parameter(description = "Source city/location", required = true)
            @RequestParam String from,
            @Parameter(description = "Destination city/location", required = true)
            @RequestParam String to) {
        return new ResponseEntity<>(busDisplayService.getBusesFromTo(from, to), HttpStatus.OK);
    }
}
