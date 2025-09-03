package com.bivolaris.userservice.dtos;


import com.bivolaris.userservice.entities.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;


public class UpdateUserRequestDto {


    private String firstName;
    private String lastName;

    @Email(message = "You must provide a valid email.")
    private String email;
    private String phone;
    private GenderEnum gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;


    private Boolean marketingEmails;
    private Boolean orderNotifications;
}
