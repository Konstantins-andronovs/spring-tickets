package com.andronov.tickets.service;

import com.andronov.tickets.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceServiceImpl(
            PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public BigDecimal getBasePrice(String terminalName) {
        return this.priceRepository.getByTerminalName(terminalName);
    }
}
