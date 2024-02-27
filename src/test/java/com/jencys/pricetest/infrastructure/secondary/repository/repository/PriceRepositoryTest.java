package com.jencys.pricetest.infrastructure.secondary.repository.repository;

import com.jencys.pricetest.core.domain.Price;
import com.jencys.pricetest.infrastructure.secondary.entity.BrandEntity;
import com.jencys.pricetest.infrastructure.secondary.entity.PriceEntity;
import com.jencys.pricetest.infrastructure.secondary.repository.dao.PriceDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceRepositoryTest {
    private static final String PATTERN = "yyyy-MM-dd-HH.mm.ss";
    PriceDAO priceDAO;
    PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        priceDAO = mock(PriceDAO.class);
        priceRepository = new PriceRepository(priceDAO);
    }

    @Test
    void given_a_price_valid_price_will_return_ok() {
        //arrange
        String testDate = "2020-06-14-00.00.00";
        Timestamp timestamp = stringToTimestamp(testDate);
        PriceEntity priceEntity = PriceEntity.builder()
                .id(1L)
                .brandEntity(new BrandEntity(1L, "XYZ"))
                .startDate(timestamp)
                .endDate(timestamp)
                .priceList(1)
                .productId(1L)
                .priority(1)
                .price(10.0)
                .currency("EUR")
                .build();

        when(priceDAO.getPrice(any(Timestamp.class), anyLong(), anyString())).thenReturn(Collections.singletonList(priceEntity));

        //act
        List<Price> response = priceRepository.getPrice(timestamp, 1L, "XYZ");

        //asserts
        assertNotNull(response);
        assertNotNull(response.get(0));
        assertEquals(response.get(0).getPrice(), 10.0);
        assertEquals(response.get(0).getBrand(), "XYZ");
        assertEquals(response.get(0).getProductIdentifier(), 1L);
    }

    private Timestamp stringToTimestamp(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return Timestamp.valueOf(dateTime);
    }
}