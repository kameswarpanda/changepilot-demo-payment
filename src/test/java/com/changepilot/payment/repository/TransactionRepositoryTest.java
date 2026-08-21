package com.changepilot.payment.repository;

import com.changepilot.payment.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    @Test
    void shouldSaveAndFindTransaction() {

        TransactionRepository repository =
                new TransactionRepository();

        Transaction transaction = new Transaction(
                "TXN-1001",
                "ORDER-1001",
                new BigDecimal("99.99"),
                "USD",
                "SUCCESS"
        );

        repository.save(transaction);

        Transaction result =
                repository.findById("TXN-1001")
                        .orElseThrow();

        assertEquals("TXN-1001", result.transactionId());
        assertEquals("ORDER-1001", result.orderId());
        assertEquals("SUCCESS", result.status());
    }

    @Test
    void shouldReturnEmptyWhenTransactionDoesNotExist() {

        TransactionRepository repository =
                new TransactionRepository();

        assertTrue(
                repository.findById("UNKNOWN")
                        .isEmpty()
        );
    }
}
