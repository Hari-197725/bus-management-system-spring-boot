package com.project.bus_reservation.models;

import com.project.bus_reservation.enums.SeatStatus;
import com.project.bus_reservation.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "bus_id", nullable = false, updatable = false)
    private Long busId;

    @NotNull
    @Positive
    @Column(name = "seat_number", nullable = false, updatable = false)
    private Integer seatNumber;


    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false)
    private SeatType seatType;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SeatStatus seatStatus;

}
