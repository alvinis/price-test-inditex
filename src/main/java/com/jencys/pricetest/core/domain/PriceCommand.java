package com.jencys.pricetest.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.With;


@Builder
@Value
@Getter
public class PriceCommand {
    @With
    String date;
    @With
    Long productIdentifier;
    @With
    String brand;


}
