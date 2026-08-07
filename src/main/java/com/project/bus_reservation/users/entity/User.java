package com.project.bus_reservation.users.entity;

import com.project.bus_reservation.models.Booking;
import com.project.bus_reservation.users.dto.request.UserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


// @Data generates more code than a JPA entity usually needs. Some of that generated code can cause unexpected problems.
// Lombok automatically generates: @Getters @Setters @toString() @equals() @hashCode() @Required constructor (if needed).
// Usually, we only need: @Getters @Setters @No-args constructor. So for this problem we can use @getter, @setter and so on. But
// if you want all the other operation and also you are gonna use DTO then should use @Data. @Data is safe for DTOs because
// DTOs are simple data containers and don't have JPA/Hibernate behaviour. Rule: @Entity → Avoid @Data; DTO → Use @Data.
@Entity
//@Data
@Getter
// @Table(name = "users") is optional if the table name matches the entity name.
// Use @Table(name = "...") when you want to explicitly specify the database table name.
// Rule: Same table name → @Table optional; Different table name → Use @Table(name = "...").
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id // @Id already implies the primary key, so the @Column settings are unnecessary here.
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
//    JPA already derives the column name from the field name in many cases. You only need to specify name when you want
//    the database column to be different from the Java field name. ex: @Column(name = "name") private String user_name.
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits.")
    private String phoneNumber; //Spring Boot (via Hibernate's default physical naming strategy) converts camelCase to snake_case.
    // So you don't need mention phoneNumber to @Column(name = "phone_number") unless you've changed the naming strategy.

    @Column(nullable = false, updatable = false)
    @CreationTimestamp  //Automatically insert the time and date when new column created.
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Builder
    private User(String email, String name, String phoneNumber) { // This is the constructor of this class.
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void updateUser(UserRequest userRequest) {
        this.name = userRequest.getName() != null ? userRequest.getName() : this.name;
        this.phoneNumber = userRequest.getPhone() != null ? userRequest.getPhone() : this.phoneNumber;
    }
}