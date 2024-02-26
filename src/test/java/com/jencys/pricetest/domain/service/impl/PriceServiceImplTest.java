package com.jencys.pricetest.domain.service.impl;

import com.jencys.pricetest.data.entity.Brand;
import com.jencys.pricetest.data.entity.Price;
import com.jencys.pricetest.data.repository.PriceRepository;
import com.jencys.pricetest.domain.service.PriceService;
import com.jencys.pricetest.presentation.dto.PriceRequestDTO;
import com.jencys.pricetest.presentation.dto.PriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceImplTest {
    private static final String PATTERN = "yyyy-MM-dd-HH.mm.ss";

    private PriceRepository priceRepository;
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        priceService = new PriceServiceImpl(priceRepository);
    }

    @Test
    void given_a_valid_date_will_return_prices() {
        //arrange
        String testDate = "2020-06-14-00.00.00";
        PriceRequestDTO priceRequestDTO = PriceRequestDTO.builder()
                .date(testDate)
                .productIdentifier(1L)
                .brand("any-brand")
                .build();
        Timestamp timestamp = stringToTimestamp(testDate);
        Price price = new Price(
                1L,
                new Brand(1L, "any-brand"),
                timestamp,
                timestamp,
                1,
                1L,
                1,
                10.0,
                "USD");

        when(priceRepository.xd(any(Timestamp.class), anyLong(), anyString())).thenReturn(Collections.singletonList(price));

        //act
        PriceResponse response = priceService.getPrice(priceRequestDTO);

        //asserts
        assertNotNull(response);
        assertEquals(price.getProductId(), response.getProductIdentifier());
        assertEquals(price.getPrice(), response.getPrice());
        assertEquals(price.getBrand().getName(), response.getBrand());
    }

    @Test
    void given_a_invalid_date_will_throw_exception() {
        //arrange
        String testDate = "2020-06-14-00.00.00";
        PriceRequestDTO priceRequestDTO = PriceRequestDTO.builder()
                .date(testDate)
                .productIdentifier(1L)
                .brand("any-brand")
                .build();

        when(priceRepository.xd(any(Timestamp.class), anyLong(), anyString())).thenReturn(Collections.emptyList());

        //act & asserts
        assertThrows(NoSuchElementException.class, () -> priceService.getPrice(priceRequestDTO));
    }

    private Timestamp stringToTimestamp(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return Timestamp.valueOf(dateTime);
    }
}

