package com.changepilot.payment.model;

public record PaymentResponse(
        String transactionId,
        String status,
        String message
) {
}
