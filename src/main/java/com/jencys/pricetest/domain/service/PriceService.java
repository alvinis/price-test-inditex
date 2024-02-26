package com.jencys.pricetest.domain.service;

import com.jencys.pricetest.presentation.dto.PriceResponse;
import com.jencys.pricetest.presentation.dto.PriceRequestDTO;

public interface PriceService {
    PriceResponse getPrice(PriceRequestDTO priceRequestDTO);
}
