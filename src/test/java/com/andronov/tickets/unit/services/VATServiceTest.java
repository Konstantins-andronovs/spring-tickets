package com.andronov.tickets.unit.services;

import com.andronov.tickets.exceptions.models.DataFetchingException;
import com.andronov.tickets.repositories.VATRepository;
import com.andronov.tickets.services.VATServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VATServiceTest {
    @Mock
    private VATRepository vatRepository;

    @InjectMocks
    private VATServiceImpl vatService;

    @Test
    void retrievedDraftPricesHaveCorrectTotalSum() throws DataFetchingException {
        BigDecimal VAT = new BigDecimal("0.21");
        when(vatRepository.getVAT()).thenReturn(VAT);

        BigDecimal response = vatService.getVAT();

        assertThat(response.doubleValue()).isEqualTo(0.21);
    }
}
