package com.andronov.tickets.repositories;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PriceRepository {
    private static final Map<String, BigDecimal> priceData = new HashMap<>();

    PriceRepository() {
        priceData.put("Vilnius, Lithuania" , new BigDecimal("10.00"));
        priceData.put("Riga, Latvia" , new BigDecimal("12.00"));
    }

    public BigDecimal getByTerminalName(String terminalName) {
        return priceData.getOrDefault(terminalName, null);
    }
}
