package com.project.bus_reservation.buses.entity;

import com.project.bus_reservation.buses.enums.BusStatus;
import com.project.bus_reservation.buses.enums.BusType;
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
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @Positive
    @Min(6)
    @Column(nullable = false, updatable = false, unique = true, length = 6)
    private Integer busNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private BusType busType;

    @Min(18)
    @Column(nullable = false, updatable = true)
    private Integer totalSeats;

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
