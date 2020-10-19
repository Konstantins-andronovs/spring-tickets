package com.andronov.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class TicketResponseDTO implements Serializable {

    private List<TicketItem> ticketPrices;
    private double totalPrice;

}
