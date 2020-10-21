package com.andronov.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO implements Serializable {
    private static final long serialVersionUID = -4455620156598999712L;

    @NotNull(message = "Terminal name is required")
    private String terminalName;

    @Valid
    @NotNull(message = "Tickets are required")
    @Size(min = 1, message = "At least one ticket is required")
    private List<TicketRequestUnitDTO> tickets;

}
