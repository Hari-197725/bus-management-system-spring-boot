package com.project.bus_reservation.operator.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.route.entity.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collectionName = "operators")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operator {
    @DocumentId
    private String id;
    private String operatorName;
    private LocalDateTime joinedAt = LocalDateTime.now();
    private List<Bus> buses = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();

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