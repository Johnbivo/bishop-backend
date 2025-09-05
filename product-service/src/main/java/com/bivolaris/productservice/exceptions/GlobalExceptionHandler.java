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


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException ex){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Category Not Found");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/categories");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }


    @ExceptionHandler(CategoryCreationException.class)
    public ResponseEntity<?> handleCategoryCreationException(CategoryCreationException ex){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Category could not be created.");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/categories");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> handleInventoryNotFoundException(InventoryNotFoundException ex){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Inventory Not Found");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/inventory");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(InventoryCreationException.class)
    public ResponseEntity<?> handleInventoryCreationException(InventoryCreationException ex){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Inventory could not be created.");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/inventory");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }


    @ExceptionHandler(ProductSpecificationsNotFoundException.class)
    public ResponseEntity<?> handleProductSpecificationsNotFoundException(ProductSpecificationsNotFoundException ex){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Product Specifications Not Found");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/product-specifications");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }


    @ExceptionHandler(ProductSpecificationsCreationException.class)
    public ResponseEntity<?> handleProductSpecificationsCreationException(ProductSpecificationsCreationException ex){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Product Specifications could not be created.");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/product-specifications");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }



}

