package com.andronov.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class TicketResponseDTO implements Serializable {
    private static final long serialVersionUID = -4468780156598999712L;

    private List<TicketResponseUnitDTO> tickets;
    private double totalPrice;

}
