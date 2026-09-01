package com.project.bus_reservation.user.repository;

import com.project.bus_reservation.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
