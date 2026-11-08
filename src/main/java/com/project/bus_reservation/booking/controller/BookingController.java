package com.project.bus_reservation.booking.controller;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.service.BookingService;
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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Booking Management", description = "Operations for creating and retrieving user bookings")
@RestController
@RequestMapping("/api/v1/user/{userId}/booking")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request - Invalid request body or parameters",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found - User or Booking not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Selected seat is already booked or unavailable",
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
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Create a booking", description = "Creates a new ticket reservation/booking for the specified user")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @PostMapping
    public ResponseEntity<Void> createBooking(
            @Parameter(description = "Unique identifier of the user")
            @PathVariable String userId,
            @Valid @RequestBody BookingCreateRequest bookingCreateRequest) {
        bookingService.createBooking(userId, bookingCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all bookings for a user", description = "Retrieves all bookings made by the specified user")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all bookings",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookingResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings(
            @Parameter(description = "Unique identifier of the user")
            @PathVariable String userId) {
        return new ResponseEntity<>(bookingService.getAllBookings(userId), HttpStatus.OK);
    }

    @Operation(summary = "Get booking by ID", description = "Retrieves details of a specific booking for a user")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved booking details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookingResponse.class)
            )
    )
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingByBookingId(
            @Parameter(description = "Unique identifier of the user")
            @PathVariable String userId,
            @Parameter(description = "Unique identifier of the booking")
            @PathVariable String bookingId) {
        return new ResponseEntity<>(bookingService.getBookingByBookingId(userId, bookingId), HttpStatus.OK);
    }
}