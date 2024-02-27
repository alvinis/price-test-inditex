package com.jencys.pricetest.infrastructure.primary.controller;

import com.jencys.pricetest.infrastructure.primary.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDTO> handleException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .fieldName(ex.getParameterName())
                .errorMessage(ex.getMessage())
                .build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> notFoundException(NoSuchElementException ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorMessage(ex.getMessage())
                .build(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDTO> illegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorMessage(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorDTO> dateTimeException(DateTimeParseException ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorMessage(String.format("Error parsing date parameter: %s. Example date format: 2020-06-06-00.00.00", ex.getMessage()))
                .build(),HttpStatus.BAD_REQUEST);
    }

}

