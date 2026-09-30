package com.project.bus_reservation.operator.repository;

import com.project.bus_reservation.operator.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    @Query(value = "select exists (select 1 from operators where operator_name = :operatorName)", nativeQuery = true)
    boolean existsByOperatorName (@Param("operatorName")String operatorName);
}
