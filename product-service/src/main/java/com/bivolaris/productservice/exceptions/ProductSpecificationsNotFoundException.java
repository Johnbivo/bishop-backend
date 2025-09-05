package com.bivolaris.productservice.exceptions;

public class ProductSpecificationsNotFoundException extends RuntimeException {
    public ProductSpecificationsNotFoundException(String message) {
        super(message);
    }
}
