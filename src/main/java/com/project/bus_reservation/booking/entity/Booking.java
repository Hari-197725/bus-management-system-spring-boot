package com.project.bus_reservation.booking.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.user.entity.User;
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
    private User user;
    private BusTrip busTrip;
    private List<BookingDetail> bookingDetails = new ArrayList<>();
}