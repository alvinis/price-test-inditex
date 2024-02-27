package com.jencys.pricetest.infrastructure.config;

import com.jencys.pricetest.core.port.PricePersistencePort;
import com.jencys.pricetest.core.service.PriceService;
import com.jencys.pricetest.core.service.impl.PriceServiceImpl;
import com.jencys.pricetest.infrastructure.secondary.repository.dao.PriceDAO;
import com.jencys.pricetest.infrastructure.secondary.repository.repository.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PricePersistencePort pricePersistencePort(PriceDAO priceDAO){
        return new PriceRepository(priceDAO);
    }

    @Bean
    public PriceService priceService(PricePersistencePort pricePersistencePort) {
        return new PriceServiceImpl(pricePersistencePort);
    }
}
