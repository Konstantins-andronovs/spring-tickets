package com.andronov.tickets.controllers;

import com.andronov.tickets.dto.TicketRequestDTO;
import com.andronov.tickets.dto.TicketResponseDTO;
import com.andronov.tickets.exceptions.models.DataFetchingException;
import com.andronov.tickets.services.TicketService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(
            TicketService ticketService
    ) {
        this.ticketService = ticketService;
    }

    @PostMapping(value = "/tickets", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<TicketResponseDTO> getDraftPrices(@Valid @RequestBody TicketRequestDTO ticketRequestDTO) throws DataFetchingException {
        return ResponseEntity.ok(ticketService.getDraftPrices(ticketRequestDTO));
    }
}

