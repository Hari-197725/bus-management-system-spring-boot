package com.project.bus_reservation.booking.entity;

import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @CreationTimestamp
    @Column(name = "booking_date", nullable = false, updatable = false)
    private LocalDate bookingDate;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2, updatable = true)
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_trip_id", nullable = false)
    private BusTrip busTrip;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    List<BookingDetail> bookingDetails = new ArrayList<>();
}