package com.project.bus_reservation.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long busId;
    private Long routeId;
    private Date travelDate;
    private Time departureTime;
    private Time arrivalTime;
    private double ticketPrice;
    private Integer availableSeats;
}
