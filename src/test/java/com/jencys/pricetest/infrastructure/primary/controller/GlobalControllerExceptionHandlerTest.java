package com.jencys.pricetest.infrastructure.primary.controller;

import com.jencys.pricetest.infrastructure.primary.dto.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalControllerExceptionHandlerTest {
    private final GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();

    @Test
    public void testHandleMissingServletRequestParameterException() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("paramName", "paramType");
        ResponseEntity<ErrorDTO> response = handler.handleException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("paramName", response.getBody().getFieldName());
        assertEquals("Required request parameter 'paramName' for method parameter type paramType is not present", response.getBody().getErrorMessage());
    }

    @Test
    public void testHandleNoSuchElementException() {
        NoSuchElementException ex = new NoSuchElementException("Element not found");
        ResponseEntity<ErrorDTO> response = handler.notFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Element not found", response.getBody().getErrorMessage());
    }

    @Test
    public void testHandleIllegalStateException() {
        IllegalStateException ex = new IllegalStateException("Illegal state");
        ResponseEntity<ErrorDTO> response = handler.illegalStateException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Illegal state", response.getBody().getErrorMessage());
    }

    @Test
    public void testHandleDateTimeParseException() {
        DateTimeParseException ex = new DateTimeParseException("Error parsing date", "", 0);
        ResponseEntity<ErrorDTO> response = handler.dateTimeException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error parsing date parameter: Error parsing date. Example date format: 2020-06-06-00.00.00", response.getBody().getErrorMessage());
    }
}