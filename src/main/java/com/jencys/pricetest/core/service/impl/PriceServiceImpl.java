package com.jencys.pricetest.core.service.impl;

import com.jencys.pricetest.core.domain.Price;
import com.jencys.pricetest.core.domain.PriceCommand;
import com.jencys.pricetest.core.port.PricePersistencePort;
import com.jencys.pricetest.core.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private static final String PATTERN = "yyyy-MM-dd-HH.mm.ss";

    private final PricePersistencePort pricePersistencePort;

    @Override
    public Price getPrice(PriceCommand priceCommand) {
        Timestamp timestamp = stringToTimestamp(priceCommand.getDate());
        return pricePersistencePort.getPrice(timestamp, priceCommand.getProductIdentifier(), priceCommand.getBrand())
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private Timestamp stringToTimestamp(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return Timestamp.valueOf(dateTime);
    }

}
