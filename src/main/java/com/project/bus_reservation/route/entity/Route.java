package com.project.bus_reservation.route.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

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

    private Timestamp createdAt = Timestamp.now();
    private Timestamp modifiedAt = Timestamp.now();

    private String operatorId;
}