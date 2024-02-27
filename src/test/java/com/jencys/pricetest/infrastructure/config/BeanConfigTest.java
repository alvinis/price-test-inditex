package com.jencys.pricetest.infrastructure.config;

import com.jencys.pricetest.core.port.PricePersistencePort;
import com.jencys.pricetest.core.service.PriceService;
import com.jencys.pricetest.infrastructure.secondary.repository.dao.PriceDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeanConfigTest {
    PriceDAO priceDAO;
    BeanConfig beanConfig;
    PricePersistencePort pricePersistencePort;

    @BeforeEach
    void setUp() {
        priceDAO = mock(PriceDAO.class);
        pricePersistencePort = mock(PricePersistencePort.class);
        beanConfig = new BeanConfig();
    }

    @Test
    void pricePersistencePortTest() {
        //arrange & act
        PricePersistencePort response = beanConfig.pricePersistencePort(priceDAO);

        //asserts
        assertNotNull(response);
    }

    @Test
    void priceServiceTestInstance() {
        //arrange & act
        PriceService response = beanConfig.priceService(pricePersistencePort);

        //asserts
        assertNotNull(response);
    }
}