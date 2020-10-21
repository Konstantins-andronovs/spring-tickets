package com.andronov.tickets.services;

import com.andronov.tickets.exceptions.models.DataFetchingException;
import com.andronov.tickets.repositories.VATRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VATServiceImpl implements VATService {
    private static final Logger logger = LoggerFactory.getLogger(VATServiceImpl.class);

    private final VATRepository vatRepository;

    @Autowired
    public VATServiceImpl(
            VATRepository vatRepository) {
        this.vatRepository = vatRepository;
    }

    @Override
    public BigDecimal getVAT() throws DataFetchingException {
        BigDecimal VAT = this.vatRepository.getVAT();
        if (VAT == null) {
            logger.info("Error fetching VAT");
            throw new DataFetchingException("There was an error fetching VAT");
        }
        return VAT;
    }
}
