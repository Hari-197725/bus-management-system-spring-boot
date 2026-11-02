package com.project.bus_reservation.route.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.operator.entity.Operator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collectionName = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @DocumentId
    private String id;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @Positive
    private double distance;

    @Positive
    private Integer estimatedDuration;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    private Operator operator;
    private List<Bus> buses = new ArrayList<>();
    private List<BusTrip> busTrips = new ArrayList<>();
}