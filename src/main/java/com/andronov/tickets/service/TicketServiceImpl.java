package com.andronov.tickets.service;

import com.andronov.tickets.dto.*;
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

import static com.andronov.tickets.dto.TicketType.*;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(VATServiceImpl.class);

    private final PriceService priceService;
    private final VATService vatService;

    Map<Integer, BigDecimal> cache = new ConcurrentHashMap<>();

    @Autowired
    public TicketServiceImpl(
            PriceService priceService,
            VATService vatService
    ) {
        this.priceService = priceService;
        this.vatService = vatService;
    }

    @Override
    public TicketResponseDTO getDraftPrices(TicketRequestDTO requestDTO) {
        BigDecimal VAT = this.vatService.getVAT();
        BigDecimal basePrice = this.priceService.getBasePrice(requestDTO.getTerminalName());

        List<TicketItem> list = new ArrayList<>();

        logger.info("Passenger processing");
        requestDTO.getPassengers().forEach(passenger -> {
            list.add(this.processPassenger(passenger, basePrice, VAT));
            if (passenger.getLuggage() > 0) {
                list.add(this.processLuggage(passenger, basePrice, VAT));
            }
        });

        double totalPrice = list.stream()
                .map(TicketItem::getPrice)
                .map(item -> item.round(new MathContext(4, RoundingMode.HALF_UP)))
                .mapToDouble(BigDecimal::doubleValue)
                .sum();

        return new TicketResponseDTO(list, totalPrice);
    }

    @Override
    public TicketItem processPassenger(PassengerDTO passenger, BigDecimal basePrice, BigDecimal VAT) {
        if (passenger.isChild()) {
            BigDecimal childVatPrice = calculateVatUnitCached(basePrice, VAT, CHILD);
            return new TicketItem(CHILD, childVatPrice);
        } else {
            BigDecimal adultVatPrice = calculateVatUnitCached(basePrice, VAT, ADULT);
            return new TicketItem(ADULT, adultVatPrice);
        }
    }

    @Override
    public TicketItem processLuggage(PassengerDTO passenger, BigDecimal basePrice, BigDecimal VAT) {
        int luggageCount = passenger.getLuggage();
        BigDecimal luggagePrice = calculateLuggageCached(basePrice, VAT, passenger.getLuggage());
        return new TicketItem(LUGGAGE, luggageCount, luggagePrice);
    }

    private BigDecimal getVatAmount(BigDecimal amount, BigDecimal VAT) {
        return amount.add(amount.multiply(VAT));
    }

    private BigDecimal calculateLuggageCached(BigDecimal basePrice, BigDecimal VAT, Integer count) {
        if (count == 1) {
            return calculateVatUnitCached(basePrice, VAT, LUGGAGE);
        }
        return this.cache.computeIfAbsent(count, f -> calculateVatUnitCached(basePrice, VAT, LUGGAGE)
                .multiply(new BigDecimal(count))
                .setScale(2, RoundingMode.HALF_UP));
    }

    private BigDecimal calculateVatUnitCached(BigDecimal basePrice, BigDecimal VAT, TicketType type) {
        BigDecimal unitVatPrice = BigDecimal.ZERO;

        if (type.equals(ADULT)) {
            unitVatPrice = this.cache.computeIfAbsent(type.value, f -> getVatAmount(basePrice, VAT)
                    .setScale(2, RoundingMode.HALF_UP)
            );
        } else if (type.equals(CHILD)) {
            unitVatPrice = this.cache.computeIfAbsent(type.value, f -> getVatAmount(basePrice, VAT)
                    .multiply(CHILD_DISCOUNT)
                    .setScale(2, RoundingMode.HALF_UP)
            );
        } else if (type.equals(LUGGAGE)) {
            unitVatPrice = this.cache.computeIfAbsent(type.value, f -> getVatAmount(basePrice.multiply(LUGGAGE_DISCOUNT), VAT)
                    .setScale(2, RoundingMode.HALF_UP)
            );
        }
        return unitVatPrice;
    }
}
