package com.andronov.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketItem implements Serializable {
    private TicketType type;
    private int count;
    private BigDecimal price;

    public TicketItem(TicketType type, BigDecimal price) {
        this.type = type;
        this.price = price;
        this.count = 1;
    }
}
