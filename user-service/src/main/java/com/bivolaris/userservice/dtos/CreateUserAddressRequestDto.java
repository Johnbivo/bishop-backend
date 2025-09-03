package com.bivolaris.userservice.dtos;


import com.bivolaris.userservice.entities.AddressTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserAddressRequestDto {

    private UUID userId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String company;
    private AddressTypeEnum addressType;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Boolean isDefault;
}
