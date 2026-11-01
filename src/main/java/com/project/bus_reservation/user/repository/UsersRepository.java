package com.project.bus_reservation.user.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRepository extends FirestoreReactiveRepository<User> {
    Mono<User> findByPhoneNumber(String phoneNumber);
    Mono<User> findByEmail(String email);
}