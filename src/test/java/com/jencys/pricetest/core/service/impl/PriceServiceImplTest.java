package com.jencys.pricetest.core.service.impl;

import com.jencys.pricetest.core.domain.Price;
import com.jencys.pricetest.core.domain.PriceCommand;
import com.jencys.pricetest.core.port.PricePersistencePort;
import com.jencys.pricetest.core.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PriceServiceImplTest {
    PricePersistencePort pricePersistencePort;
    PriceService priceService;

    @BeforeEach
    void setUp() {
       pricePersistencePort = mock(PricePersistencePort.class);
       priceService = new PriceServiceImpl(pricePersistencePort);
    }

    @Test
    void given_valid_response_will_return_ok() {
        //arrange
        Price price = Price.builder()
                .brand("XYZ")
                .price(10.0)
                .productIdentifier(1L)
                .build();

        PriceCommand priceCommand = PriceCommand.builder()
                .date("2020-01-14-00.00.00")
                .productIdentifier(1L)
                .brand("XYZ")
                .build();

        when(pricePersistencePort.getPrice(any(Timestamp.class), anyLong(), anyString())).thenReturn(Collections.singletonList(price));

        //act
        Price response = priceService.getPrice(priceCommand);

        //asserts
        assertNotNull(response);
        assertEquals(response.getProductIdentifier(), 1L);
        assertEquals(response.getBrand(), "XYZ");
        assertEquals(response.getPrice(), 10.0);
    }

    @Test
    void given_a_empty_response_will_throw_exception() {
        //arrange
        PriceCommand priceCommand = PriceCommand.builder()
                .date("2020-01-14-00.00.00")
                .productIdentifier(1L)
                .brand("XYZ")
                .build();

        when(pricePersistencePort.getPrice(any(Timestamp.class), anyLong(), anyString())).thenReturn(Collections.emptyList());

        //act & asserts
        assertThrows(NoSuchElementException.class, () -> priceService.getPrice(priceCommand));
    }
}