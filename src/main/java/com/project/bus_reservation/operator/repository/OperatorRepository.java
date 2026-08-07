package com.project.bus_reservation.operator.repository;

import com.project.bus_reservation.operator.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
