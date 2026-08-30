package com.changepilot.payment.model;

import com.changepilot.payment.validation.ISO4217Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank String orderId,
        @Positive BigDecimal amount,
        @NotBlank @ISO4217Currency String currency
) {
}
