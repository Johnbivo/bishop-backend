package com.bivolaris.authservice.dtos;


import lombok.Data;

@Data
public class CreateAuthAccountResponse {

    private Boolean status;
    private String message;
}
