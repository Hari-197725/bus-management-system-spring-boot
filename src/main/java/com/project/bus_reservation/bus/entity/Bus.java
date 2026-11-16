package com.project.bus_reservation.bus.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bus.enums.BusStatus;
import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.seats.entity.Seat;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Document(collectionName = "buses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
    @DocumentId
    private String id;

    private Integer busNumber;

    private String busName;

    private BusType busType;

    private Integer totalSeats;

    private BusStatus status;

    private Timestamp createdAt = Timestamp.now();
    private Timestamp modifiedAt = Timestamp.now();

    private String operatorId;
    private String routeId;

    private List<Seat> seats = new ArrayList<>();
}