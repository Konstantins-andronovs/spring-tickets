package com.andronov.tickets.services;

import com.andronov.tickets.exceptions.models.DataFetchingException;

import java.math.BigDecimal;

public interface PriceService {

    BigDecimal getBasePrice(String terminalName) throws DataFetchingException;
}
