package com.project.bus_reservation.bus.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bus.enums.BusStatus;
import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.seats.entity.Seat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
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

    @NotNull
    @Positive
    private Integer busNumber;

    @NotBlank
    private String busName;

    private BusType busType;

    @Min(18)
    private Integer totalSeats;

    private BusStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    private Operator operator;
    private Route route;

    private List<Seat> seats = new ArrayList<>();
    private List<BusTrip> busTrips = new ArrayList<>();

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