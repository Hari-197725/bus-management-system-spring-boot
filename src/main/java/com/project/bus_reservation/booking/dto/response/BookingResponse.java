package com.project.bus_reservation.booking.dto.response;

import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.passenger.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "Booking details response")
public class BookingResponse {
    @Schema(description = "Unique identifier of the booking")
    private Long id;

    @Schema(description = "Date when the booking was made")
    private LocalDate bookingDate;

    @Schema(description = "Total booking amount")
    private BigDecimal amount;

    @Schema(description = "Name of the user who made the booking")
    private String userName;

    @Schema(description = "Bus trip details for the booking")
    private BusTripResponse busTripResponse;

    @Schema(description = "List of booking detail entries with passenger and seat info")
    private List<BookingDetailResponse> bookingDetailResponses;

    @Getter
    @AllArgsConstructor
    @Schema(description = "Bus trip details associated with a booking")
    public static class BusTripResponse {
        @Schema(description = "Departure date and time")
        private LocalDateTime departureTime;

        @Schema(description = "Arrival date and time")
        private LocalDateTime arrivalTime;

        @Schema(description = "Name of the bus")
        private String busName;

        @Schema(description = "Type of the bus")
        private BusType busType;

        @Schema(description = "Name of the bus operator")
        private String OperatorName;

        @Schema(description = "Source city/location")
        private String source;

        @Schema(description = "Destination city/location")
        private String destination;

        @Schema(description = "Estimated travel duration in minutes")
        private Integer estimatedDuration;
    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "Booking detail with passenger and seat information")
    public static class BookingDetailResponse {
        @Schema(description = "Age of the passenger")
        private Integer passengerAge;

        @Schema(description = "Gender of the passenger")
        private Gender passengerGender;

        @Schema(description = "Name of the passenger")
        private String passengerName;

        @Schema(description = "Assigned seat number")
        private Integer seatNumber;
    }
}
