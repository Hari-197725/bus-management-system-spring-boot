package com.project.bus_reservation.models;

import com.project.bus_reservation.common.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking_details")
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "booking_id", nullable = false, updatable = false)
    private Long bookingId;

    @Column(name = "seat_id", nullable = false, updatable = false)
    private Long seatId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "passenger_name", nullable = false)
    private String passengerName;

    @Min(0)
    @Max(100)
    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, updatable = false)
    private Gender gender;
}