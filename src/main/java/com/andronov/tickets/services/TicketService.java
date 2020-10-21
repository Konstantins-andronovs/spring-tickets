package com.andronov.tickets.services;

import com.andronov.tickets.dto.TicketRequestUnitDTO;
import com.andronov.tickets.dto.TicketResponseUnitDTO;
import com.andronov.tickets.dto.TicketRequestDTO;
import com.andronov.tickets.dto.TicketResponseDTO;
import com.andronov.tickets.enums.TicketType;
import com.andronov.tickets.exceptions.models.DataFetchingException;

import java.math.BigDecimal;

public interface TicketService {
    BigDecimal CHILD_DISCOUNT = new BigDecimal("0.5");
    BigDecimal LUGGAGE_DISCOUNT = new BigDecimal("0.3");

    TicketResponseDTO getDraftPrices(TicketRequestDTO requestDTO) throws DataFetchingException;

    BigDecimal calculateVatUnitCached(BigDecimal basePrice, BigDecimal VAT, TicketType type, Integer count);
}
