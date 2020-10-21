package com.andronov.tickets.unit.services;

import com.andronov.tickets.dto.TicketRequestUnitDTO;
import com.andronov.tickets.dto.TicketResponseUnitDTO;
import com.andronov.tickets.dto.TicketRequestDTO;
import com.andronov.tickets.dto.TicketResponseDTO;
import com.andronov.tickets.exceptions.models.DataFetchingException;
import com.andronov.tickets.services.PriceService;
import com.andronov.tickets.services.TicketServiceImpl;
import com.andronov.tickets.services.VATService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static com.andronov.tickets.enums.TicketType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private PriceService priceService;
    @Mock
    private VATService vatService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private BigDecimal VAT;
    private BigDecimal basePrice;

    @BeforeEach
    public void setUp() {
        VAT = new BigDecimal("0.21");
        basePrice = new BigDecimal("10.00");

    }

    @Test
    void retrievedDraftPricesHaveCorrectTotalSum() throws DataFetchingException {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO();
        ticketRequestDTO.setTerminalName("terminalNameTest");
        TicketRequestUnitDTO ticket = new TicketRequestUnitDTO(ADULT, 1);
        TicketRequestUnitDTO ticket2 = new TicketRequestUnitDTO(LUGGAGE, 2);
        ticketRequestDTO.setTickets(Arrays.asList(ticket, ticket2));

        when(vatService.getVAT()).thenReturn(VAT);
        when(priceService.getBasePrice(any(String.class))).thenReturn(basePrice);

        TicketResponseDTO response = ticketService.getDraftPrices(ticketRequestDTO);

        assertThat(response.getTickets().size()).isEqualTo(2);
        assertThat(response.getTotalPrice()).isEqualTo(19.36);
    }

    @Test
    void processedTicketHaveChildDiscountPrice() {
        TicketRequestUnitDTO ticketUnit = new TicketRequestUnitDTO(CHILD, 1);

        BigDecimal ticket = ticketService.calculateVatUnitCached(basePrice, VAT, ticketUnit.getType(), ticketUnit.getCount());

        assertThat(ticket.doubleValue()).isEqualTo(6.05);
    }

    @Test
    void processedTicketHaveLuggageDiscountPrice() {
        TicketRequestUnitDTO ticketUnit = new TicketRequestUnitDTO(LUGGAGE, 1);

        BigDecimal ticket = ticketService.calculateVatUnitCached(basePrice, VAT, ticketUnit.getType(), ticketUnit.getCount());

        assertThat(ticket.doubleValue()).isEqualTo(3.63);
    }

    @Test
    void processedTicketHaveMultipleLuggagePrice() {
        TicketRequestUnitDTO ticketUnit = new TicketRequestUnitDTO(LUGGAGE, 4);

        BigDecimal ticket = ticketService.calculateVatUnitCached(basePrice, VAT, ticketUnit.getType(), ticketUnit.getCount());

        assertThat(ticket.doubleValue()).isEqualTo(14.52);
    }
}
