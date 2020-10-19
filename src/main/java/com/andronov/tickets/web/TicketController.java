package com.andronov.tickets.web;

import com.andronov.tickets.dto.TicketRequestDTO;
import com.andronov.tickets.dto.TicketResponseDTO;
import com.andronov.tickets.service.TicketService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "This is bad request, please follow API documentation for the proper request format"),
        @ApiResponse(code = 401, message = "Due to security constraints your access request cannot be authorized"),
        @ApiResponse(code = 500, message = "The server is down. Please make sure that the Dog microservice is up and running")

})
public class TicketController {
    private TicketService ticketService;

    @Autowired
    public TicketController(
            TicketService ticketService
    ) {
        this.ticketService = ticketService;
    }


//    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(value = "/tickets", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<TicketResponseDTO> approveLoan(@RequestBody TicketRequestDTO ticketRequestDTO) {
//        try {
            return ResponseEntity.ok(ticketService.getDraftPrices(ticketRequestDTO));
//        } catch (ManagerNotPermittedException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, "This manager is not allowed to approve this request", e);
//        }
    }
}

