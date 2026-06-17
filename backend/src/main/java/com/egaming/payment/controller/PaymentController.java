package com.egaming.payment.controller;

import com.egaming.payment.dto.PaymentRequest;
import com.egaming.payment.dto.WithdrawalRequest;
import com.egaming.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment and wallet endpoints")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/wallet")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get wallet balance")
    public ResponseEntity<?> getWallet() {
        return ResponseEntity.ok(paymentService.getWallet());
    }

    @PostMapping("/deposit")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Deposit to wallet")
    public ResponseEntity<?> depositToWallet(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.depositToWallet(request));
    }

    @PostMapping("/withdraw")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Withdraw from wallet")
    public ResponseEntity<?> withdrawFromWallet(@Valid @RequestBody WithdrawalRequest request) {
        return ResponseEntity.ok(paymentService.withdrawFromWallet(request));
    }

    @GetMapping("/transactions")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get transaction history")
    public ResponseEntity<?> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(paymentService.getTransactions(page, size));
    }

    @GetMapping("/transaction/{transactionId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get transaction details")
    public ResponseEntity<?> getTransaction(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.getTransaction(transactionId));
    }

    @PostMapping("/withdraw/{withdrawalId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update withdrawal status")
    public ResponseEntity<?> updateWithdrawalStatus(@PathVariable String withdrawalId, @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updateWithdrawalStatus(withdrawalId, status));
    }
}
