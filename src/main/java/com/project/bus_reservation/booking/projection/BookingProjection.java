package com.project.bus_reservation.booking.projection;

import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.passenger.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BookingProjection {
    Long getBookingId();

    LocalDate getBookingDate();

    BigDecimal getAmount();

    String getUserName();

    LocalDateTime getDepartureTime();

    LocalDateTime getArrivalTime();

    String getBusName();

    BusType getBusType();

    String getOperatorName();

    String getSource();

    String getDestination();

    Integer getEstimatedDuration();

    Integer getPassengerAge();

    Gender getPassengerGender();

    String getPassengerName();

    Integer getSeatNumber();
}