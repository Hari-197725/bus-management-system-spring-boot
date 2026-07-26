package com.project.bus_reservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bus")
public class Bus {
    @Id
    private Long id;
    private Integer busNumber;
    private Integer busType;
    private Integer totalSeats;
    private String operatorNames;
    private Integer status;
}
