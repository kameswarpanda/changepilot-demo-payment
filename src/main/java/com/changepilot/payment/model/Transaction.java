package com.changepilot.payment.model;

import java.math.BigDecimal;

public record Transaction(
        String transactionId,
        String orderId,
        BigDecimal amount,
        String currency,
        String status
) {
}
