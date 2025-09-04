package com.bivolaris.productservice.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex){
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("timestamp", LocalDateTime.now().format(formatter));
            errorBody.put("status", HttpStatus.NOT_FOUND.value());
            errorBody.put("error", "Product Not Found");
            errorBody.put("message", ex.getMessage());
            errorBody.put("path", "/products");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
        }


    @ExceptionHandler(ProductCreationException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductCreationException ex){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Product could not be created.");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/products");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }



}

