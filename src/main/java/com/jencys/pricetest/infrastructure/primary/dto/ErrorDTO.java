package com.jencys.pricetest.infrastructure.primary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String fieldName;
    private String errorMessage;
}
