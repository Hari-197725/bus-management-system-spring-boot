package com.project.bus_reservation.user.repository;

import com.project.bus_reservation.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT EXISTS (SELECT 1 FROM users WHERE phone_number = :phoneNumber)", nativeQuery = true)
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}