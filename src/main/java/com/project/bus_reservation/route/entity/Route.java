package com.project.bus_reservation.route.entity;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.operator.entity.Operator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "routes", uniqueConstraints = {@UniqueConstraint(columnNames = {"operator_id", "source", "destination"})})
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotBlank
    private String source;

    @NotBlank
    @Column(name = "destination", nullable = false)
    private String destination;

    @Positive
    @Column(name = "distance", nullable = false)
    private double distance;

    @Positive
    @Column(nullable = false)
    private Integer estimatedDuration;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "operator_id")
    private Operator operator;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route", orphanRemoval = true)
    private List<Bus> buses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    private List<BusTrip> busTrips = new ArrayList<>();
}