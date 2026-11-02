package com.project.bus_reservation.bookingdetail.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.seats.entity.Seat;
import lombok.*;

@Document(collectionName = "booking_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetail {
    @DocumentId
    private String id;
    private Booking booking;
    private Passenger passenger;
    private Seat seat;
}