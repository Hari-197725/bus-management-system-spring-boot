package com.project.bus_reservation.route.entity;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.operator.entity.Operator;
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
@Table(name = "routes")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "operator_id")
    private Operator operator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}