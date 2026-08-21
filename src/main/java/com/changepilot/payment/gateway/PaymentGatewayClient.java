package com.changepilot.payment.gateway;

import com.changepilot.payment.model.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentGatewayClient {

    public String charge(PaymentRequest request) {

        // Simulates a successful external payment gateway call.
        return UUID.randomUUID().toString();
    }
}
