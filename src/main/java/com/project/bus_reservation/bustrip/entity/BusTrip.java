package com.project.bus_reservation.bustrip.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.route.entity.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Document(collectionName = "bus_trips")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusTrip {
    @DocumentId
    private String id;
    private LocalDateTime departureTime = LocalDateTime.now();
    private LocalDateTime arrivalTime = LocalDateTime.now().plusHours(4);
    private Bus bus;
    private Route route;
    private Booking bookings;
}