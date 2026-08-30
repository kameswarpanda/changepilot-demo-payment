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
    void shouldProcessPaymentSuccessfullyWithUSD() {
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

    @Test
    void shouldProcessPaymentSuccessfullyWithEUR() {
        PaymentRequest request = new PaymentRequest(
                "ORDER-1002",
                new BigDecimal("150.00"),
                "EUR"
        );

        when(paymentGatewayClient.charge(request))
                .thenReturn("gateway-ref-456");

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

    @Test
    void shouldProcessPaymentSuccessfullyWithGBP() {
        PaymentRequest request = new PaymentRequest(
                "ORDER-1003",
                new BigDecimal("75.50"),
                "GBP"
        );

        when(paymentGatewayClient.charge(request))
                .thenReturn("gateway-ref-789");

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

    @Test
    void shouldProcessPaymentSuccessfullyWithJPY() {
        PaymentRequest request = new PaymentRequest(
                "ORDER-1004",
                new BigDecimal("10000"),
                "JPY"
        );

        when(paymentGatewayClient.charge(request))
                .thenReturn("gateway-ref-012");

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

    @Test
    void shouldThrowIllegalArgumentExceptionForNon3LetterCurrency() {
        PaymentRequest request = new PaymentRequest(
                "ORDER-1005",
                new BigDecimal("25.00"),
                "EU" // Invalid: not 3 letters
        );

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.processPayment(request)
        );

        assertTrue(thrown.getMessage().contains("Invalid ISO-4217 currency code"));
        verify(paymentGatewayClient, never()).charge(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForInvalid3LetterCurrency() {
        PaymentRequest request = new PaymentRequest(
                "ORDER-1006",
                new BigDecimal("50.00"),
                "XYZ" // Invalid: not a real ISO code
        );

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.processPayment(request)
        );

        assertTrue(thrown.getMessage().contains("Invalid ISO-4217 currency code"));
        verify(paymentGatewayClient, never()).charge(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNullCurrency() {
        PaymentRequest request = new PaymentRequest(
                "ORDER-1007",
                new BigDecimal("10.00"),
                null // Invalid: null currency
        );

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.processPayment(request)
        );

        assertTrue(thrown.getMessage().contains("Invalid ISO-4217 currency code"));
        verify(paymentGatewayClient, never()).charge(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForBlankCurrency() {
        PaymentRequest request = new PaymentRequest(
                "ORDER-1008",
                new BigDecimal("10.00"),
                "" // Invalid: blank currency
        );

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.processPayment(request)
        );

        assertTrue(thrown.getMessage().contains("Invalid ISO-4217 currency code"));
        verify(paymentGatewayClient, never()).charge(any());
        verify(transactionRepository, never()).save(any());
    }
}
