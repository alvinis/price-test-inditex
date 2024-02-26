package com.jencys.pricetest.presentation.controller;

import com.jencys.pricetest.domain.service.PriceService;
import com.jencys.pricetest.presentation.dto.PriceResponse;
import com.jencys.pricetest.presentation.dto.PriceRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final PriceService priceService;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(@RequestParam String date, @RequestParam Long productIdentifier, @RequestParam String brand){
        return ResponseEntity.ok(priceService.getPrice(PriceRequestDTO.builder()
                .date(date)
                .productIdentifier(productIdentifier)
                .brand(brand)
                .build()));
    }
}
