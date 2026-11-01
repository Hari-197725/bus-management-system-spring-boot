package com.project.bus_reservation.operator.entity;

import com.google.cloud.firestore.annotation.DocumentId;
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
    private String firebaseUid;
    private String operatorName;
    private LocalDateTime joinedAt;
    private List<Bus> buses = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
}