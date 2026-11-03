package com.project.bus_reservation.route.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.route.entity.Route;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RouteRepository extends FirestoreReactiveRepository<Route> {
    Flux<Route> findByOperatorId(String operatorId);
}