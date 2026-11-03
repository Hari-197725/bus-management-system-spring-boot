package com.project.bus_reservation.operator.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collectionName = "operators")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operator {
    @DocumentId
    private String id;
    private String operatorName;
    private Timestamp joinedAt;
}