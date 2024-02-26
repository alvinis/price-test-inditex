package com.jencys.pricetest.domain.service.impl;

import com.jencys.pricetest.data.repository.PriceRepository;
import com.jencys.pricetest.domain.service.PriceService;
import com.jencys.pricetest.presentation.dto.PriceResponse;
import com.jencys.pricetest.presentation.dto.PriceRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private static final String PATTERN = "yyyy-MM-dd-HH.mm.ss";

    private final PriceRepository priceRepository;

    @Override
    public PriceResponse getPrice(@Valid PriceRequestDTO priceRequestDTO) {
        Timestamp timestamp = stringToTimestamp(priceRequestDTO.getDate());
        return priceRepository.xd(timestamp, priceRequestDTO.getProductIdentifier(), priceRequestDTO.getBrand())
                .stream()
                .findFirst()
                .map(price -> PriceResponse.builder()
                        .productIdentifier(price.getProductId())
                        .price(price.getPrice())
                        .brand(price.getBrand().getName())
                        .build())
                .orElseThrow();
    }

    private Timestamp stringToTimestamp(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return Timestamp.valueOf(dateTime);
    }
}
