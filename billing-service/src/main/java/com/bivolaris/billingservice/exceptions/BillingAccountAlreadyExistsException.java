package com.bivolaris.billingservice.exceptions;

public class BillingAccountAlreadyExistsException extends RuntimeException {
    public BillingAccountAlreadyExistsException(String message) {
        super(message);
    }
}
