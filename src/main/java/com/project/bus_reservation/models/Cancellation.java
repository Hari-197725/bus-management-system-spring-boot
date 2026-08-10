package com.project.bus_reservation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cancellations")
public class Cancellation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

//    @Column(name = "booking_id", nullable = false, updatable = false)
//    private Long bookingId;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @CreationTimestamp
    @Column(name = "cancelled_at", nullable = false, updatable = false)
    private LocalDateTime cancelledAt;

    @Size(max = 100)
    @Column(name = "reason", nullable = true)
    private String reason;

    //      Precision = Total digits.
//    Scale = Digits after the decimal.
//    Digits before the decimal = precision - scale.
    @DecimalMin("0.00")
    @Column(name = "refund_amount", precision = 10, scale = 2)
    private BigDecimal refundAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
