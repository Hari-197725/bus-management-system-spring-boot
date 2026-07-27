package com.project.bus_reservation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false, updatable = false, unique = true)
    private Integer busNumber;

    @Column(nullable = false, updatable = false)
    private Integer busType;

    @Min(5)
    @Column(nullable = false, updatable = false)
    private Integer totalSeats;

    @Column(nullable = false, updatable = true)
    private String operatorNames;

    @Column(nullable = false, updatable = true)
    private Integer status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
