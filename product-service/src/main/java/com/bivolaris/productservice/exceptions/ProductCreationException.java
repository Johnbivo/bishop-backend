package com.bivolaris.productservice.exceptions;

public class ProductCreationException extends RuntimeException {
    public ProductCreationException(String message) {
        super(message);
    }
}
