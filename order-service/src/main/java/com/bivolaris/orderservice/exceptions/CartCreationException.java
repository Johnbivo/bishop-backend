package com.bivolaris.orderservice.exceptions;

public class CartCreationException extends RuntimeException {
    public CartCreationException(String message) {
        super(message);
    }
}
