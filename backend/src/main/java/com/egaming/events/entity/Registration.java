package com.egaming.events.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
@UniqueConstraint(columnNames = {"event_id", "user_id"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private Long userId;

    private Long teamId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status; // REGISTERED, CONFIRMED, CANCELLED, REJECTED

    private Boolean hasPaymentVerified = false;

    private String paymentTransactionId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime confirmedAt;

    private LocalDateTime cancelledAt;

    public enum RegistrationStatus {
        REGISTERED, CONFIRMED, CANCELLED, REJECTED, DISQUALIFIED
    }
}
