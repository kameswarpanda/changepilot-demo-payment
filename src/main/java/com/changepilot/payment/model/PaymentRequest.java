package com.changepilot.payment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank String orderId,
        @Positive BigDecimal amount,
        @NotBlank String currency
) {
}
