package com.project.bus_reservation.operator.repository;

import com.project.bus_reservation.operator.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    @Modifying
    @Query(value = "delete from operators where id = :operatorId", nativeQuery = true)
     int deleteOperatorById(@Param("operatorId") Long operatorId);
}
