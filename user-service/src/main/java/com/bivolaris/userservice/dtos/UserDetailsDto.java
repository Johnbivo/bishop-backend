package com.bivolaris.userservice.dtos;


import com.bivolaris.userservice.entities.BillingAccountStatusEnum;
import com.bivolaris.userservice.entities.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class UserDetailsDto {

    private UUID id;
    private String firstName;
    private String lastName;


    private String email;
    private String phone;
    private GenderEnum gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private UUID billingAccountId;
    private BillingAccountStatusEnum billingAccountStatus;
    private LocalDateTime billingCreatedAt;

    private Boolean marketingEmails;
    private Boolean orderNotifications;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;





}
