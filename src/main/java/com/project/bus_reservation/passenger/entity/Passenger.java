package com.project.bus_reservation.passenger.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.spring.data.firestore.Document;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.passenger.enums.Gender;
import com.project.bus_reservation.user.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Document(collectionName = "passengers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @DocumentId
    private String id;

    @Min(3)
    @Max(100)
    @NotNull
    private Integer age;

    private Gender gender;

    @NotBlank
    @Size(max = 20)
    private String name;

    private User user;

    private List<BookingDetail> bookingDetails = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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