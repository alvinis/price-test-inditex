package com.jencys.pricetest.presentation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPrice() throws Exception {
        mockMvc.perform(get("/api/v1/product")
                        .param("date", "2020-06-14-00.00.00")
                        .param("productIdentifier", "35455")
                        .param("brand", "XYZ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productIdentifier").value(35455))
                .andExpect(jsonPath("$.brand").value("XYZ"))
                .andExpect(jsonPath("$.price").value(35.5));
    }
}