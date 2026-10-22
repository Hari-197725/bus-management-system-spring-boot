package com.project.bus_reservation.cancellation.controller;

import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.cancellation.service.CancellationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/{userId}/booking/{bookingId}/cancel")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class CancellationController {
    @Autowired
    CancellationService cancellationService;

    @ApiResponse(responseCode = "204", description = "Booking cancelled successfully")
    @DeleteMapping
    public ResponseEntity<Void> cancelBookingByBookingId(@PathVariable Long userId, @PathVariable Long bookingId, @RequestBody CancellationRequest cancellationRequest){
        cancellationService.cancelBookingByBookingId(userId, bookingId, cancellationRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
