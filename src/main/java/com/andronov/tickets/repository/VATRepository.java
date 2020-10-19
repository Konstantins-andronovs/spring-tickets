package com.andronov.tickets.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VATRepository {
    private static final Map<LocalDate, BigDecimal> vatData = new HashMap<>();


    VATRepository() {
        vatData.put(LocalDate.now() , new BigDecimal("0.21"));
    }

    public BigDecimal getVAT() {
        return vatData.getOrDefault(LocalDate.now(), BigDecimal.ZERO);
    }
}
