package com.andronov.tickets.service;

import com.andronov.tickets.repository.VATRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VATServiceImpl implements VATService {

    private final VATRepository vatRepository;

    @Autowired
    public VATServiceImpl(
            VATRepository vatRepository) {
        this.vatRepository = vatRepository;
    }

    @Override
    public BigDecimal getVAT() {
        return this.vatRepository.getVAT();
    }
}
