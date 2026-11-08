package com.project.bus_reservation.cancellation.controller;

import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.cancellation.service.CancellationService;
import com.project.bus_reservation.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cancellation Management", description = "Operations for cancelling bus ticket bookings")
@RestController
@RequestMapping("/api/v1/user/{userId}/booking/{bookingId}/cancel")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request - Invalid cancellation parameters or payload",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found - Booking or User not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Booking cannot be cancelled",
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
public class CancellationController {
    @Autowired
    CancellationService cancellationService;

    @Operation(summary = "Cancel a booking", description = "Cancels an existing ticket booking for the specified user and releases the seats")
    @ApiResponse(responseCode = "204", description = "Booking cancelled successfully")
    @DeleteMapping
    public ResponseEntity<Void> cancelBookingByBookingId(
            @Parameter(description = "Unique identifier of the user")
            @PathVariable String userId,
            @Parameter(description = "Unique identifier of the booking to cancel")
            @PathVariable String bookingId,
            @RequestBody CancellationRequest cancellationRequest) {
        cancellationService.cancelBookingByBookingId(userId, bookingId, cancellationRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

