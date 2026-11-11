package com.project.bus_reservation.busseats.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.busseats.service.BusSeatsService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses/{busId}/seats")
@Tag(name = "Bus Seats")
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
public class BusSeatsController {

    @Autowired
    BusSeatsService busSeatsService;

    @Operation(summary = "Get all seats for a bus", description = "Retrieves all seats belonging to a specific bus")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved seats for the bus",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BusResponse.SeatResponse.class))))
    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>> getSeatsByBusId(
            @Parameter(description = "ID of the bus", required = true)
            @PathVariable Long busId) {
        return new ResponseEntity<>(busSeatsService.getSeatsByBusId(busId), HttpStatus.OK);
    }

    @Operation(summary = "Get seat by ID", description = "Retrieves details of a specific seat in a specific bus")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved seat details",
            content = @Content(schema = @Schema(implementation = BusResponse.SeatResponse.class)))
    @GetMapping("/{seatId}")
    public ResponseEntity<BusResponse.SeatResponse> getSeatBySeatId(
            @Parameter(description = "ID of the bus", required = true)
            @PathVariable Long busId,
            @Parameter(description = "ID of the seat to retrieve", required = true)
            @PathVariable Long seatId) {
        return new ResponseEntity<>(busSeatsService.getSeatBySeatId(busId, seatId), HttpStatus.OK);
    }
}
