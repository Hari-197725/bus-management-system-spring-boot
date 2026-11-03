package com.project.bus_reservation.seats.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

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

    private String busId;

    public void markAsBooked() {
        this.seatStatus = SeatStatus.BOOKED;
    }

    public void markAsAvailable() {
        this.seatStatus = SeatStatus.AVAILABLE;
    }
}