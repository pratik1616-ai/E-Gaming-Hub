package com.egaming.referral.controller;

import com.egaming.referral.service.ReferralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/referral")
@RequiredArgsConstructor
@Tag(name = "Referral", description = "Referral program endpoints")
public class ReferralController {
    private final ReferralService referralService;

    @GetMapping("/code")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get referral code")
    public ResponseEntity<?> getReferralCode() {
        return ResponseEntity.ok(referralService.getReferralCode());
    }

    @GetMapping("/stats")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get referral stats")
    public ResponseEntity<?> getReferralStats() {
        return ResponseEntity.ok(referralService.getReferralStats());
    }

    @PostMapping("/use/{referralCode}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Use referral code")
    public ResponseEntity<?> useReferralCode(@PathVariable String referralCode) {
        return ResponseEntity.ok(referralService.useReferralCode(referralCode));
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Get referral leaderboard")
    public ResponseEntity<?> getReferralLeaderboard() {
        return ResponseEntity.ok(referralService.getReferralLeaderboard());
    }
}
