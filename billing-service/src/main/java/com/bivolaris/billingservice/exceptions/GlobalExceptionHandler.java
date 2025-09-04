package com.bivolaris.billingservice.exceptions;

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

    @ExceptionHandler(BillingAccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsersNotFound(BillingAccountNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "User Not Found");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/billing-accounts");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }


    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleInvoiceNotFound(InvoiceNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Invoice Not Found");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/invoices");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }


    @ExceptionHandler(BillingAccountAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleBillingAccountAlreadyExists(BillingAccountAlreadyExistsException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.CONFLICT.value());
        errorBody.put("error", "Billing Account Already Exists");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/billing-accounts");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorBody);
    }
}
