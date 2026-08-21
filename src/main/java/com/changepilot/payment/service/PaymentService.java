package com.changepilot.payment.service;

import com.changepilot.payment.gateway.PaymentGatewayClient;
import com.changepilot.payment.model.PaymentRequest;
import com.changepilot.payment.model.PaymentResponse;
import com.changepilot.payment.model.Transaction;
import com.changepilot.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

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

        String transactionId = UUID.randomUUID().toString();

        String gatewayReference =
                paymentGatewayClient.charge(request);

        Transaction transaction = new Transaction(
                transactionId,
                request.orderId(),
                request.amount(),
                request.currency(),
                "SUCCESS"
        );

        transactionRepository.save(transaction);

        return new PaymentResponse(
                transactionId,
                "SUCCESS",
                "Payment processed successfully"
        );
    }
}
