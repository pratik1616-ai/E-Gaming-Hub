package com.egaming.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, UPI
    private Long eventId; // Optional, if paying for event
}
