package com.changepilot.payment.service;

import com.changepilot.payment.gateway.PaymentGatewayClient;
import com.changepilot.payment.model.PaymentRequest;
import com.changepilot.payment.model.PaymentResponse;
import com.changepilot.payment.model.Transaction;
import com.changepilot.payment.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentGatewayClient paymentGatewayClient;
    private final TransactionRepository transactionRepository;

    public PaymentService(
            PaymentGatewayClient paymentGatewayClient,
            TransactionRepository transactionRepository
    ) {
        this.paymentGatewayClient = paymentGatewayClient;
        this.transactionRepository = transactionRepository;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        logger.info("Processing payment request: {}", request);

        // Explicit currency validation to ensure service robustness
        // even if declarative validation is bypassed or not fully configured.
        try {
            Currency.getInstance(request.currency());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid currency code received: {}", request.currency(), e);
            throw new IllegalArgumentException("Invalid ISO-4217 currency code: " + request.currency(), e);
        }

        String transactionId = UUID.randomUUID().toString();

        String gatewayReference =
                paymentGatewayClient.charge(request);

        logger.info("Payment gateway charged successfully. Gateway Reference: {}", gatewayReference);

        Transaction transaction = new Transaction(
                transactionId,
                request.orderId(),
                request.amount(),
                request.currency(),
                "SUCCESS"
        );

        transactionRepository.save(transaction);
        logger.info("Transaction saved: {}", transaction);

        PaymentResponse response = new PaymentResponse(
                transactionId,
                "SUCCESS",
                "Payment processed successfully"
        );
        logger.info("Payment processed successfully for orderId: {}. Response: {}", request.orderId(), response);
        return response;
    }
}
