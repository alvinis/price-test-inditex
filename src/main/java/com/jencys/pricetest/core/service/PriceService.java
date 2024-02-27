package com.jencys.pricetest.core.service;

import com.jencys.pricetest.core.domain.Price;
import com.jencys.pricetest.core.domain.PriceCommand;

public interface PriceService {
    Price getPrice(PriceCommand price);
}
