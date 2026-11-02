package com.project.bus_reservation.seats.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Document(collectionName = "seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @DocumentId
    private String id;

    @NotNull
    @Positive
    private Integer seatNumber;

    private SeatType seatType;

    private SeatStatus seatStatus;

    private Bus bus;

    private List<BookingDetail> bookingDetails = new ArrayList<>();

    public void markAsBooked() {
        this.seatStatus = SeatStatus.BOOKED;
    }

    public void markAsAvailable() {
        this.seatStatus = SeatStatus.AVAILABLE;
    }
}