package com.project.bus_reservation.route.repository;

import com.project.bus_reservation.route.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
