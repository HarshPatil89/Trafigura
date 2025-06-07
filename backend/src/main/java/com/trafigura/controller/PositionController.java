package com.trafigura.controller;

import com.trafigura.model.Transaction;
import com.trafigura.model.Position;
import com.trafigura.service.PositionService;
import com.trafigura.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/addTransaction")
    public void addTransactions(@RequestBody Transaction transaction) {
        transaction.setTransactionType("INSERT");
        transactionService.save(transaction);
    }

    @PostMapping("/updateTransaction")
    public void updateTransactions(@RequestBody Transaction transaction) {
        transaction.setTransactionType("UPDATE");
        transactionService.update(transaction);
    }

    @PostMapping("/cancelTransaction")
    public void cancelTransactions(@RequestBody Transaction transaction) {
        transaction.setTransactionType("CANCEL");
        transactionService.update(transaction);
    }

    @GetMapping("/transactions")
    public List<Transaction> transactions() {
        return transactionService.findAll();
    }

    @GetMapping("/positions")
    public List<Position> getPositions() {
        return positionService.getPositions();
    }

    @PostMapping("/init")
    public void initSampleData() {
        transactionService.initSampleData();
    }

}
