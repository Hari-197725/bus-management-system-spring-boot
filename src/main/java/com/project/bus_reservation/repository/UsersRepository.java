package com.project.bus_reservation.repository;

import com.project.bus_reservation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
