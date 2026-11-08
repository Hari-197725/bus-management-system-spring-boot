package com.project.bus_reservation.seats.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.seats.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Seat Management", description = "Operations for retrieving global seat information across all buses")
@RestController
@RequestMapping("/api/v1/seat")
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
                description = "Not Found - No seats found",
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
public class SeatController {

    @Autowired
    SeatService seatService;

    @Operation(summary = "Get all seats from all buses", description = "Retrieves all seats across all registered buses in the system")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all seats",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BusResponse.SeatResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>> getAllSeats() {
        return new ResponseEntity<>(seatService.getAllSeatsFromAllBus(), HttpStatus.OK);
    }
}