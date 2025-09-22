package com.bivolaris.authservice.dtos;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String oldPassword;

    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String newPassword;

    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String confirmNewPassword;

}
