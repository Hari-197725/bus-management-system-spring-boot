package com.project.bus_reservation.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "booking_id", nullable = false, updatable = false)
    private Long bookingId;


    private Integer ticketNumber;
    private Date issueAt;
}
