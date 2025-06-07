package com.trafigura.service;

import com.trafigura.model.Transaction;
import com.trafigura.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        transaction.setVersion(1);
        return transactionRepository.save(transaction);
    }

    public Transaction update(Transaction transaction) {
        List<Transaction> transactionOptional = transactionRepository.findByTradeId(transaction.getTransactionId());
        if (!transactionOptional.isEmpty()) {
            transaction.setVersion(2);
            transactionRepository.save(transaction);
        }
        return transaction;
    }


    public List<Transaction> saveAll(List<Transaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(int transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public void deleteById(int transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public void initSampleData() {
        List<Transaction> transactions = findAll();
        transactions.addAll(Arrays.asList(
                new Transaction(1, 1, 1, "REL", 50, "INSERT", "Buy"),
                new Transaction(2, 2, 1, "ITC", 40, "INSERT", "Sell"),
                new Transaction(3, 3, 1, "INF", 70, "INSERT", "Buy"),
                new Transaction(4, 1, 2, "REL", 60, "UPDATE", "Buy"),
                new Transaction(5, 2, 2, "ITC", 30, "CANCEL", "Buy"),
                new Transaction(6, 4, 1, "INF", 20, "INSERT", "Sell")
        ));
        saveAll(transactions);
    }
}
