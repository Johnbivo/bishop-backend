package com.bivolaris.userservice.exceptions;

public class UserAddressNotFoundException extends RuntimeException {
    public UserAddressNotFoundException(String message) {
        super(message);
    }
}
