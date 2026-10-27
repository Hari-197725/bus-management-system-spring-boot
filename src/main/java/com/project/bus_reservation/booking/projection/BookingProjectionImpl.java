package com.project.bus_reservation.booking.projection;

import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.passenger.enums.Gender;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingProjectionImpl implements BookingProjection {
    private Long bookingId;
    private LocalDate bookingDate;
    private BigDecimal amount;
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
