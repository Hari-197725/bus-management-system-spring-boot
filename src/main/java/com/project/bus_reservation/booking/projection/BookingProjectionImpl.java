package com.project.bus_reservation.booking.projection;

import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.passenger.enums.Gender;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingProjectionImpl implements BookingProjection {
    private String bookingId;
    private LocalDate bookingDate;
    private Double amount;
    private String userName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String busName;
    private BusType busType;
    private String operatorName;
    private String source;
    private String destination;
    private Integer estimatedDuration;
    private Integer passengerAge;
    private Gender passengerGender;
    private String passengerName;
    private Integer seatNumber;
}
