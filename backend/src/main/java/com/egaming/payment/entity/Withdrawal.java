package com.egaming.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "withdrawals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String withdrawalId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WithdrawalStatus status; // PENDING, APPROVED, PROCESSING, COMPLETED, REJECTED

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    private String upiId;

    private String withdrawalMethod; // BANK_TRANSFER, UPI, CRYPTO

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private String rejectionReason;

    public enum WithdrawalStatus {
        PENDING, APPROVED, PROCESSING, COMPLETED, REJECTED, CANCELLED
    }
}
