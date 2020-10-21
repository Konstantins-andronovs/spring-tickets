package com.andronov.tickets.unit.services;

import com.andronov.tickets.exceptions.models.DataFetchingException;
import com.andronov.tickets.repositories.PriceRepository;
import com.andronov.tickets.services.PriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void retrievedDraftPricesHaveCorrectTotalSum() throws DataFetchingException {
        BigDecimal basePrice = new BigDecimal("10.00");
        when(priceRepository.getByTerminalName(any(String.class))).thenReturn(basePrice);

        BigDecimal response = priceService.getBasePrice("terminalName");

        assertThat(response.doubleValue()).isEqualTo(10.00);
    }
}
