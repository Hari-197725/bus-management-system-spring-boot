package com.project.bus_reservation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "User")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Email
    @Column(name = "email", nullable = false, updatable = true)
    private String email;

    @NotBlank
    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits.")
    private long phoneNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp //Automatically insert the time and date when new column created.
    private LocalDateTime createdAt;

    @Column(name = "modified_At", nullable = false)
    @CreationTimestamp
    private LocalDateTime modifiedAt;
}
