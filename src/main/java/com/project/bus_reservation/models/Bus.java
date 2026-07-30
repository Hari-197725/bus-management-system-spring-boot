package com.project.bus_reservation.models;

import com.project.bus_reservation.enums.BusStatus;
import com.project.bus_reservation.enums.BusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Data
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false, updatable = false, unique = true)
    private Integer busNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private BusType busType;

    @Min(5)
    @Column(nullable = false, updatable = true)
    private Integer totalSeats;

    @NotBlank
    @Column(nullable = false, updatable = true)
    private String operatorNames;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = true)
    private BusStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
