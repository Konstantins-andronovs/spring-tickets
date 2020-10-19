package com.andronov.tickets.unit.services;

import com.andronov.tickets.dto.PassengerDTO;
import com.andronov.tickets.dto.TicketItem;
import com.andronov.tickets.dto.TicketRequestDTO;
import com.andronov.tickets.dto.TicketResponseDTO;
import com.andronov.tickets.service.PriceService;
import com.andronov.tickets.service.TicketServiceImpl;
import com.andronov.tickets.service.VATService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static com.andronov.tickets.dto.TicketType.*;
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
    private TicketRequestDTO ticketRequestDTO;
    private PassengerDTO passenger;

    @BeforeEach
    public void setUp() {
        VAT = new BigDecimal("0.21");
        basePrice = new BigDecimal("10.00");

        ticketRequestDTO = new TicketRequestDTO();
        ticketRequestDTO.setTerminalName("terminalNameTest");
        passenger = new PassengerDTO(false, 2);
        ticketRequestDTO.setPassengers(Collections.singletonList(passenger));
    }

    @Test
    void retrievedDraftPricesHaveCorrectTotalSum() {
        when(vatService.getVAT()).thenReturn(VAT);
        when(priceService.getBasePrice(any(String.class))).thenReturn(basePrice);

        TicketResponseDTO response = ticketService.getDraftPrices(ticketRequestDTO);

        assertThat(response.getTicketPrices().size()).isEqualTo(2);
        assertThat(response.getTotalPrice()).isEqualTo(19.3);
    }

    @Test
    void processedPassengerHaveCorrectPrice() {
        TicketItem ticket = ticketService.processPassenger(passenger, basePrice, VAT);

        assertThat(ticket.getPrice().doubleValue()).isEqualTo(12.1);
        assertThat(ticket.getType()).isEqualTo(ADULT);
        assertThat(ticket.getCount()).isEqualTo(1);
    }

    @Test
    void processedPassengerHaveChildDiscount() {
        passenger.setChild(true);
        TicketItem ticket = ticketService.processPassenger(passenger, basePrice, VAT);

        assertThat(ticket.getPrice().doubleValue()).isEqualTo(6.05);
        assertThat(ticket.getType()).isEqualTo(CHILD);
        assertThat(ticket.getCount()).isEqualTo(1);
    }

    @Test
    void processedLuggageHaveCorrectPrice() {
        TicketItem ticket = ticketService.processLuggage(passenger, basePrice, VAT);

        assertThat(ticket.getPrice().doubleValue()).isEqualTo(7.26);
        assertThat(ticket.getType()).isEqualTo(LUGGAGE);
        assertThat(ticket.getCount()).isEqualTo(2);
    }
}
