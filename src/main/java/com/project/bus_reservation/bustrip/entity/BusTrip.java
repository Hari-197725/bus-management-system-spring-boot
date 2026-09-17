package com.project.bus_reservation.bustrip.entity;

import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.route.entity.Route;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bus_trips")
public class BusTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "departure_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "bus_id", foreignKey = @ForeignKey(name = "fk_bus_trip_bus_id"))
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "route_id", foreignKey = @ForeignKey(name = "fk_bus_trip_route_id"))
    private Route route;

    @OneToOne(mappedBy = "busTrip")
    private Booking bookings;
}