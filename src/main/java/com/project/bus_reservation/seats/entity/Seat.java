package com.project.bus_reservation.seats.entity;

import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "bus_id")
    private Bus bus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seat")
    List<BookingDetail> bookingDetails = new ArrayList<>();
}