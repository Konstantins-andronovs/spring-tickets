package com.andronov.tickets.services;

import com.andronov.tickets.dto.TicketRequestDTO;
import com.andronov.tickets.dto.TicketResponseDTO;
import com.andronov.tickets.dto.TicketResponseUnitDTO;
import com.andronov.tickets.enums.TicketType;
import com.andronov.tickets.exceptions.models.DataFetchingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.andronov.tickets.enums.TicketType.*;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final PriceService priceService;
    private final VATService vatService;

    private final Map<String, BigDecimal> cache = new ConcurrentHashMap<>();

    @Autowired
    public TicketServiceImpl(
            PriceService priceService,
            VATService vatService
    ) {
        this.priceService = priceService;
        this.vatService = vatService;
    }

    @Override
    public TicketResponseDTO getDraftPrices(TicketRequestDTO requestDTO) throws DataFetchingException {
        BigDecimal VAT = this.vatService.getVAT();
        BigDecimal basePrice = this.priceService.getBasePrice(requestDTO.getTerminalName());

        List<TicketResponseUnitDTO> list = new ArrayList<>();

        logger.info("Processing tickets");
        requestDTO.getTickets().forEach(ticket -> {
            BigDecimal unitVatPrice = calculateVatUnitCached(basePrice, VAT, ticket.getType(), ticket.getCount());
            list.add(new TicketResponseUnitDTO(ticket.getType(), ticket.getCount(), unitVatPrice));
        });

        logger.info("Calculating total price");
        double totalPrice = list.stream()
                .map(TicketResponseUnitDTO::getPrice)
                .map(item -> item.setScale(2, RoundingMode.HALF_UP))
                .mapToDouble(BigDecimal::doubleValue)
                .sum();

        this.cache.clear();

        return new TicketResponseDTO(list, totalPrice);
    }

    @Override
    public BigDecimal calculateVatUnitCached(BigDecimal basePrice, BigDecimal VAT, TicketType type, Integer count) {
        BigDecimal unitVatPrice = BigDecimal.ZERO;
        String cacheKey = count.toString() + type.toString();

        if (type.equals(ADULT)) {
            unitVatPrice = this.cache.computeIfAbsent(cacheKey, f -> getVatAmount(basePrice, VAT)
                    .multiply(new BigDecimal(count))
                    .setScale(2, RoundingMode.HALF_UP)
            );
        } else if (type.equals(CHILD)) {
            unitVatPrice = this.cache.computeIfAbsent(cacheKey, f -> getVatAmount(basePrice, VAT)
                    .multiply(CHILD_DISCOUNT)
                    .multiply(new BigDecimal(count))
                    .setScale(2, RoundingMode.HALF_UP)
            );
            unitVatPrice = unitVatPrice.multiply(new BigDecimal(count));
        } else if (type.equals(LUGGAGE)) {
            unitVatPrice = this.cache.computeIfAbsent(cacheKey, f -> getVatAmount(basePrice.multiply(LUGGAGE_DISCOUNT), VAT)
                    .multiply(new BigDecimal(count))
                    .setScale(2, RoundingMode.HALF_UP)
            );
        }
        return unitVatPrice;
    }

    private BigDecimal getVatAmount(BigDecimal amount, BigDecimal VAT) {
        return amount.add(amount.multiply(VAT)).setScale(2, RoundingMode.HALF_UP);
    }


}
