package com.bivolaris.authservice.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.UUID;

@Data
public class CreateAuthAccountRequest {

    private UUID userId;

    @Email(message = "Must be a valid email.")
    private String email;


    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;
}
