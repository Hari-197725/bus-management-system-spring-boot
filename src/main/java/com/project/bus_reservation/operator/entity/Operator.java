package com.project.bus_reservation.operator.entity;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.route.entity.Route;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "operators")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String operatorName;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime joinedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operator")
    private List<Bus> buses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operator")
    private List<Route> routes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operator")
    private List<BusTrip> busTrips = new ArrayList<>();
}