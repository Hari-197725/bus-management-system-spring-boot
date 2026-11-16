package com.project.bus_reservation.user.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collectionName = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @DocumentId
    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}