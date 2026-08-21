package com.changepilot.payment.service;

import com.changepilot.payment.gateway.PaymentGatewayClient;
import com.changepilot.payment.model.PaymentRequest;
import com.changepilot.payment.model.PaymentResponse;
import com.changepilot.payment.model.Transaction;
import com.changepilot.payment.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentGatewayClient paymentGatewayClient;

    @Mock
    private TransactionRepository transactionRepository;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(
                paymentGatewayClient,
                transactionRepository
        );
    }

    @Test
    void shouldProcessPaymentSuccessfully() {

        PaymentRequest request = new PaymentRequest(
                "ORDER-1001",
                new BigDecimal("99.99"),
                "USD"
        );

        when(paymentGatewayClient.charge(request))
                .thenReturn("gateway-ref-123");

        PaymentResponse response =
                paymentService.processPayment(request);

        assertNotNull(response);
        assertEquals("SUCCESS", response.status());
        assertEquals(
                "Payment processed successfully",
                response.message()
        );

        verify(paymentGatewayClient, times(1))
                .charge(request);

        verify(transactionRepository, times(1))
                .save(any(Transaction.class));
    }
}
