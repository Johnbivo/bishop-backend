package com.bivolaris.userservice.dtos;


import com.bivolaris.userservice.entities.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;


@Data
public class CreateUserRequestDto {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "You must provide a valid email.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    private String phone;

    private GenderEnum gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;


    private Boolean marketingEmails;
    private Boolean orderNotifications;


}
