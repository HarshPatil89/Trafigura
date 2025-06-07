package com.trafigura.service;

import com.trafigura.model.Transaction;
import com.trafigura.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PositionService {
    @Autowired
    private TransactionService transactionService;

    public List<Position> getPositions() {
        List<Transaction> transactions = transactionService.findAll();
        Map<Integer, Transaction> latestTrade = new HashMap<>();
        for (Transaction tx : transactions) {
            Transaction prev = latestTrade.get(tx.getTradeId());
            if (prev == null || tx.getVersion() > prev.getVersion()) {
                latestTrade.put(tx.getTradeId(), tx);
            }
        }
        Map<String, Integer> positionMap = new HashMap<>();
        for (Transaction tx : latestTrade.values()) {
            int sign = "Buy".equalsIgnoreCase(tx.getBuySell()) ? 1 : -1;
            positionMap.put(
                tx.getSecurityCode(),
                    !"CANCEL".equalsIgnoreCase(tx.getTransactionType()) ?
                            positionMap.getOrDefault(tx.getSecurityCode(), 0) + sign * tx.getQuantity() : 0
            );
        }
        List<Position> positions = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : positionMap.entrySet()) {
            positions.add(new Position(entry.getKey(), entry.getValue()));
        }
        return positions;
    }

}
