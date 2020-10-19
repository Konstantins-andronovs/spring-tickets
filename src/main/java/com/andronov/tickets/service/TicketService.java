package com.andronov.tickets.service;

import com.andronov.tickets.dto.PassengerDTO;
import com.andronov.tickets.dto.TicketItem;
import com.andronov.tickets.dto.TicketRequestDTO;
import com.andronov.tickets.dto.TicketResponseDTO;

import java.math.BigDecimal;

public interface TicketService {
    BigDecimal CHILD_DISCOUNT = new BigDecimal("0.5");
    BigDecimal LUGGAGE_DISCOUNT = new BigDecimal("0.3");

    TicketResponseDTO getDraftPrices(TicketRequestDTO requestDTO);

    TicketItem processPassenger(PassengerDTO passengerDTO, BigDecimal basePrice, BigDecimal VAT);
    TicketItem processLuggage(PassengerDTO passengerDTO, BigDecimal basePrice, BigDecimal VAT);
}
