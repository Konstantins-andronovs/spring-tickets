package com.andronov.tickets.unit.controllers;

import com.andronov.tickets.service.TicketServiceImpl;
import com.andronov.tickets.web.TicketController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    private TicketServiceImpl ticketService;

    @InjectMocks
    private TicketController ticketController;

//    private Loan loan;
//
//    @BeforeEach
//    public void setUp() {
//        Map<String, Boolean> approvals = new HashMap<>();
//        approvals.put("managerTest", false);
//        approvals.put("managerTest2", false);
//
//        loan = new Loan();
//        loan.setCustomerId("customerIdTest");
//        loan.setAmount(BigDecimal.TEN.setScale(2, BigDecimal.ROUND_DOWN));
//        loan.setApprovers(approvals);
//        loan.setCreatedDate(LocalDateTime.now());
//        loan.setStatus(LoanStatus.PENDING);
//    }

    @Test
    void loanCreated() {
//        LoanDTO loanDTO = new LoanDTO("customerIdTest", BigDecimal.TEN, toArray("manager"));
//        when(ticketService.getDraftPrices(any(TicketRequestDTO.class))).thenReturn(loan);
//        ResponseEntity<Loan> loanResponse = loanController.createLoan(loanDTO);
//        assertThat(loanResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(Objects.requireNonNull(loanResponse.getBody()).getStatus()).isEqualTo(LoanStatus.PENDING);
    }
}
