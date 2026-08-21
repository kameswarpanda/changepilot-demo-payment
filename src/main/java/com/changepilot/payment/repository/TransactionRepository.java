package com.changepilot.payment.repository;

import com.changepilot.payment.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {

    private final Map<String, Transaction> transactions =
            new ConcurrentHashMap<>();

    public Transaction save(Transaction transaction) {
        transactions.put(transaction.transactionId(), transaction);
        return transaction;
    }

    public Optional<Transaction> findById(String transactionId) {
        return Optional.ofNullable(transactions.get(transactionId));
    }
}
