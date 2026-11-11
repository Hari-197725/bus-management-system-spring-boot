package com.project.bus_reservation.booking.controller;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.service.BookingService;
import com.project.bus_reservation.exception.response.ErrorResponse;
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

@RestController
@RequestMapping("/api/v1/user/{userId}/booking")
@Tag(name = "Booking Management")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Resource conflict (e.g., seat already booked)",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Create a new booking", description = "Creates a new booking for the specified user with route, bus, and passenger-seat assignments")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @PostMapping
    public ResponseEntity<Void> createBooking(
            @Parameter(description = "ID of the user making the booking", required = true)
            @PathVariable Long userId,
            @Valid @RequestBody BookingCreateRequest bookingCreateRequest) {
        bookingService.createBooking(userId, bookingCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all bookings for a user", description = "Retrieves all bookings made by the specified user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all bookings",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookingResponse.class))))
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getAllBookings(userId), HttpStatus.OK);
    }

    @Operation(summary = "Get booking by ID", description = "Retrieves details of a specific booking for the given user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved booking details",
            content = @Content(schema = @Schema(implementation = BookingResponse.class)))
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingByBookingId(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "ID of the booking to retrieve", required = true, example = "1")
            @PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.getBookingByBookingId(userId, bookingId), HttpStatus.OK);
    }
}
