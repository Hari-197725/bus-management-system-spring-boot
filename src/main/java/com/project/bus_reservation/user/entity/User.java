package com.project.bus_reservation.user.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.passenger.entity.Passenger;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collectionName = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @DocumentId
    private String firebaseUid;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits.")
    private String phoneNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Booking> bookings = new ArrayList<>();
    private List<Passenger> passengers = new ArrayList<>();
}