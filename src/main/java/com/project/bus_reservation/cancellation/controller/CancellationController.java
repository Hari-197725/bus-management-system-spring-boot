package com.project.bus_reservation.cancellation.controller;

import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.cancellation.service.CancellationService;
import com.project.bus_reservation.exception.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/{userId}/booking/{bookingId}/cancel")
@Tag(name = "Cancellation")
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
public class CancellationController {
    @Autowired
    CancellationService cancellationService;

    @Operation(summary = "Cancel a booking", description = "Cancels specific seats from a booking by providing seat numbers. Releases the cancelled seats back to available status.")
    @ApiResponse(responseCode = "204", description = "Booking cancelled successfully")
    @DeleteMapping
    public ResponseEntity<Void> cancelBookingByBookingId(
            @Parameter(description = "ID of the user who owns the booking")
            @PathVariable Long userId,
            @Parameter(description = "ID of the booking to cancel")
            @PathVariable Long bookingId,
            @RequestBody CancellationRequest cancellationRequest) {
        cancellationService.cancelBookingByBookingId(userId, bookingId, cancellationRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
