package com.project.bus_reservation.operator.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.operator.entity.Operator;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OperatorRepository extends FirestoreReactiveRepository<Operator> {
    Mono<Operator> findByOperatorName(String operatorName);
}
