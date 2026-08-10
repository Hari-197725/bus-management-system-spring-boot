package com.project.bus_reservation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Data
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotBlank
    private String source;

    @NotBlank
    @Column(name = "destination", nullable = false, updatable = true)
    private String destination;

    @NotBlank
    @Positive
    @Column(name = "distance_km", nullable = false, updatable = true)
    private double distanceKm;

    @Positive
    @Column(name = "estimated_duration", nullable = false, updatable = true)
    private Integer estimatedDuration;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false, updatable = true)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}