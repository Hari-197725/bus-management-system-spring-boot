package com.project.bus_reservation.booking.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate bookingDate;
    private Double amount;
    private User user;
    private BusTrip busTrip;
    private List<BookingDetail> bookingDetails = new ArrayList<>();


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