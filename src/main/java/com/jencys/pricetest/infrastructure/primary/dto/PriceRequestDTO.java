package com.jencys.pricetest.infrastructure.primary.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PriceRequestDTO {
    @NotBlank @NotEmpty @NotNull
    private String date;
    @NotNull @Min(1)
    private Long productIdentifier;
    @NotBlank @NotEmpty @NotNull
    private String brand;
}
