package com.project.bus_reservation.repository;

import com.project.bus_reservation.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
