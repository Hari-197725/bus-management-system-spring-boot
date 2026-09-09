package com.project.bus_reservation.passenger.entity;

import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.passenger.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Min(3)
    @Max(100)
    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, updatable = false)
    private Gender gender;

    @NotBlank
    @Size(max = 20)
    @Column(name = "name", nullable = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passenger")
    private List<BookingDetail> bookingDetails = new ArrayList<>();
}