package com.andronov.tickets.dto;

import com.andronov.tickets.enums.TicketType;
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
public class TicketResponseUnitDTO implements Serializable {
    private static final long serialVersionUID = -4456799956598999712L;

    private TicketType type;
    private int count;
    private BigDecimal price;

    public TicketResponseUnitDTO(TicketType type, BigDecimal price) {
        this.type = type;
        this.price = price;
        this.count = 1;
    }
}
