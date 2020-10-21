package com.andronov.tickets.dto;

import com.andronov.tickets.enums.TicketType;
import com.andronov.tickets.util.validation.EnumNamePattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TicketRequestUnitDTO implements Serializable {
    private static final long serialVersionUID = -5655620154878999712L;

    @NotNull(message = "Ticket type is required")
    @EnumNamePattern(regexp = "ADULT|CHILD|LUGGAGE")
    private TicketType type;
    @Min(1)
    private int count;
}
