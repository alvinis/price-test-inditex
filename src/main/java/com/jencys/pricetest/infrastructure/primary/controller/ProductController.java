package com.jencys.pricetest.infrastructure.primary.controller;

import com.jencys.pricetest.core.domain.Price;
import com.jencys.pricetest.core.domain.PriceCommand;
import com.jencys.pricetest.core.service.PriceService;
import com.jencys.pricetest.infrastructure.primary.dto.PriceRequestDTO;
import com.jencys.pricetest.infrastructure.primary.dto.PriceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final PriceService priceService;
    private final Validator validator;


    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(@RequestParam String date, @RequestParam Long productIdentifier, @RequestParam String brand) throws MissingServletRequestParameterException {
        PriceRequestDTO priceRequestDTO = PriceRequestDTO.builder()
                .date(date)
                .productIdentifier(productIdentifier)
                .brand(brand)
                .build();
        Errors errors = new BeanPropertyBindingResult(priceRequestDTO, "priceRequestDTO");
        validator.validate(priceRequestDTO, errors);

        Price price = priceService.getPrice(PriceCommand.builder()
                .date(date)
                .productIdentifier(productIdentifier)
                .brand(brand)
                .build());

        return ResponseEntity.ok(PriceResponse.builder()
                .price(price.getPrice())
                .brand(price.getBrand())
                .productIdentifier(price.getProductIdentifier())
                .build());
    }
}
