package com.egaming.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WithdrawalRequest {
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String withdrawalMethod; // BANK_TRANSFER, UPI
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
}
