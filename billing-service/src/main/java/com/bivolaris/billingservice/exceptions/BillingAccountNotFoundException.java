package com.bivolaris.billingservice.exceptions;

public class BillingAccountNotFoundException extends RuntimeException {
    public BillingAccountNotFoundException(String message) {
        super(message);
    }
}
