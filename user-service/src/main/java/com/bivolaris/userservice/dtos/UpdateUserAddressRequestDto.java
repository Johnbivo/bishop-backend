package com.bivolaris.userservice.dtos;


import com.bivolaris.userservice.entities.AddressTypeEnum;
import lombok.Data;



@Data
public class UpdateUserAddressRequestDto {


    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private AddressTypeEnum addressType;
    private Boolean isDefault;
}
