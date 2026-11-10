package com.project.bus_reservation.booking.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.passenger.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.google.cloud.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Document(collectionName = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @DocumentId
    private String id;
    private Timestamp bookingDate;
    private Double amount;
    private String userId;
    private String userName;
    private EmbeddedBusTrip busTrip;
    private List<EmbeddedBookingDetail> bookingDetails = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmbeddedBusTrip {
        private Timestamp departureTime;
        private Timestamp arrivalTime;
        private String busName;
        private BusType busType;
        private String operatorName;
        private String source;
        private String destination;
        private Integer estimatedDuration;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmbeddedBookingDetail {
        private Integer passengerAge;
        private Gender passengerGender;
        private String passengerName;
        private Integer seatNumber;
    }
}
