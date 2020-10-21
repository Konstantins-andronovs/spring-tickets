package com.andronov.tickets.services;

import com.andronov.tickets.exceptions.models.DataFetchingException;
import com.andronov.tickets.repositories.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceServiceImpl implements PriceService {
    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final PriceRepository priceRepository;

    @Autowired
    public PriceServiceImpl(
            PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public BigDecimal getBasePrice(String terminalName) throws DataFetchingException {
        BigDecimal basePrice = this.priceRepository.getByTerminalName(terminalName);

        if (basePrice == null) {
            logger.info("Error fetching base price");
            throw new DataFetchingException("There was an error fetching base price for: " + terminalName);
        }
        return basePrice;
    }
}
