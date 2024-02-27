package com.jencys.pricetest.infrastructure.primary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void given_a_valid_parameters_will_return_200() throws Exception {
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

    @Test
    void given_a_invalid_date_format_will_return_400() throws Exception {
        mockMvc.perform(get("/api/v1/product")
                        .param("date", "2020-06-14-00.00.0")
                        .param("productIdentifier", "35455")
                        .param("brand", "XYZ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorMessage").value("Error parsing date parameter: Text '2020-06-14-00.00.0' could not be parsed at index 17. Example date format: 2020-06-06-00.00.00"));
    }

    @Test
    void given_a_empty_date_will_return_400() throws Exception {
        mockMvc.perform(get("/api/v1/product")
                        .param("productIdentifier", "35455")
                        .param("brand", "XYZ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorMessage").value("Required request parameter 'date' for method parameter type String is not present"))
                .andExpect(jsonPath("$.fieldName").value("date"));

    }

    @Test
    void given_a_empty_productIdentifier_will_return_400() throws Exception {
        mockMvc.perform(get("/api/v1/product")
                        .param("date", "2020-06-14-00.00.00")
                        .param("brand", "XYZ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorMessage").value("Required request parameter 'productIdentifier' for method parameter type Long is not present"))
                .andExpect(jsonPath("$.fieldName").value("productIdentifier"));

    }

    @Test
    void given_a_empty_brand_will_return_400() throws Exception {
        mockMvc.perform(get("/api/v1/product")
                        .param("date", "2020-06-14-00.00.00")
                        .param("productIdentifier", "35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorMessage").value("Required request parameter 'brand' for method parameter type String is not present"))
                .andExpect(jsonPath("$.fieldName").value("brand"));

    }

    @Test
    void given_a_valid_parameters_but_not_found_entity_will_return_404() throws Exception {
        mockMvc.perform(get("/api/v1/product")
                        .param("date", "2020-01-14-00.00.00")
                        .param("productIdentifier", "35455")
                        .param("brand", "XYZ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("No value present"));
    }
}