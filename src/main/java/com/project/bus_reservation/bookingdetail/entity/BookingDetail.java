package com.project.bus_reservation.bookingdetail.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Long getNumericId() {
        try {
            return id != null ? Long.parseLong(id) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Exclude
    public void setNumericId(Long numericId) {
        this.id = numericId != null ? String.valueOf(numericId) : null;
    }
}