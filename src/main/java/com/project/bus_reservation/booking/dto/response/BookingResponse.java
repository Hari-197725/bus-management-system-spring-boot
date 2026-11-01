package com.project.bus_reservation.booking.dto.response;

import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.passenger.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class BookingResponse {
    private String bookingId;
    private LocalDate bookingDate;
    private Double amount;
    private String userName;
    private BusTripResponse busTripResponse;
    private List<BookingDetailResponse> bookingDetailResponses;

    @Getter
    @AllArgsConstructor
    public static class BusTripResponse {
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private String busName;
        private BusType busType;
        private String OperatorName;
        private String source;
        private String destination;
        private Integer estimatedDuration;
    }

    @Getter
    @AllArgsConstructor
    public static class BookingDetailResponse {
        private Integer passengerAge;
        private Gender passengerGender;
        private String passengerName;
        private Integer seatNumber;
    }
}
