package com.project.bus_reservation.models;

import com.project.bus_reservation.enums.ScheduleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "bus_id", nullable = false, updatable = false)
    private Long busId;

    @Column(name = "route_id", nullable = false, updatable = false)
    private Long routeId;

    @NotNull
    @Column(name = "travel_Date", nullable = false, updatable = true)
    private LocalDate travelDate;

    @NotNull
    @Column(name = "departure_time")
    private LocalTime departureTime;

    @NotNull
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @NotNull
    @DecimalMin("1.0")
    @Column(name = "ticket_price", precision = 10, scale = 2)
    private BigDecimal ticketPrice;

    @Min(1)
    @Column(name = "available_seats")
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, updatable = true)
    private ScheduleStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false, updatable = true)
    private LocalDateTime updateAt;
}