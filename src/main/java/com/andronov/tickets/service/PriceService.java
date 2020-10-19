package com.andronov.tickets.service;

import java.math.BigDecimal;

public interface PriceService {

    BigDecimal getBasePrice(String terminalName);
}
