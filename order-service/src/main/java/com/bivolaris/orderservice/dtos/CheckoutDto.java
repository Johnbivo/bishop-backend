package com.bivolaris.orderservice.dtos;

import lombok.Data;

@Data
public class CheckoutDto {
    // Guest user information (null for logged-in users)
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    // Billing address (null for logged-in users)
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingCity;
    private String billingState;
    private String billingPostalCode;
    private String billingCountry;

    // Shipping address (null for logged-in users)
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingState;
    private String shippingPostalCode;
    private String shippingCountry;

    // Payment info (optional)
    private String paymentMethodId;
}