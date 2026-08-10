package com.project.bus_reservation.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

//    This entity is useless once get conformation from hari then remove this.
@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "booking_id", nullable = false, updatable = false)
    private Long bookingId;


    @Column(name = "ticket_number", unique = true, nullable = false, updatable = false)
    private Integer ticketNumber;

    @CreationTimestamp
    @Column(name = "issue_at", nullable = false, updatable = false)
    private LocalDateTime issueAt;
}