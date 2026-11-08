package com.project.bus_reservation.busseats.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.busseats.service.BusSeatsService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Bus Seats Management", description = "Operations for retrieving seats for a specific bus")
@RestController
@RequestMapping("/api/v1/buses/{busId}/seats")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request - Invalid parameters",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found - Bus or Seat not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Conflict with seat data",
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
public class BusSeatsController {

    @Autowired
    BusSeatsService busSeatsService;

    @Operation(summary = "Get seats by bus ID", description = "Retrieves all seats and their current status for a given bus")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved seats for the bus",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BusResponse.SeatResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>> getSeatsByBusId(
            @Parameter(description = "Unique identifier of the bus")
            @PathVariable String busId) {
        return new ResponseEntity<>(busSeatsService.getSeatsByBusId(busId), HttpStatus.OK);
    }

    @Operation(summary = "Get seat by ID", description = "Retrieves details of a specific seat in a given bus")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved seat details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BusResponse.SeatResponse.class)
            )
    )
    @GetMapping("/{seatId}")
    public ResponseEntity<BusResponse.SeatResponse> getSeatBySeatId(
            @Parameter(description = "Unique identifier of the bus")
            @PathVariable String busId,
            @Parameter(description = "Unique identifier of the seat")
            @PathVariable String seatId) {
        return new ResponseEntity<>(busSeatsService.getSeatBySeatId(busId, seatId), HttpStatus.OK);
    }
}