package com.jencys.pricetest.core.port;

import com.jencys.pricetest.core.domain.Price;

import java.sql.Timestamp;
import java.util.List;

public interface PricePersistencePort {
    List<Price> getPrice(Timestamp date, Long id, String brand);
}
